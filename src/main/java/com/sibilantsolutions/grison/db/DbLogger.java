package com.sibilantsolutions.grison.db;

import com.sibilantsolutions.grison.db.config.CamParams;
import com.sibilantsolutions.grison.db.handler.CamSessionHolder;
import com.sibilantsolutions.grison.db.handler.ImageRecordingValve;
import com.sibilantsolutions.grison.db.persistence.dao.ChangelogDao;
import com.sibilantsolutions.grison.db.persistence.entity.CamAlarm;
import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import com.sibilantsolutions.grison.db.persistence.repository.CamAlarmRepository;
import com.sibilantsolutions.grison.db.persistence.repository.CamSessionRepository;
import com.sibilantsolutions.grison.db.web.dto.AlarmStatusDto;
import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;
import com.sibilantsolutions.grison.db.web.dto.StreamStatusDto;
import com.sibilantsolutions.grison.db.web.websocket.AlarmBroadcaster;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionEvt;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;
import com.sibilantsolutions.utils.util.DurationLoggingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class DbLogger
{

    final static private Logger log = LoggerFactory.getLogger( DbLogger.class );

    @Autowired
    CamSessionRepository camSessionRepository;

    @Autowired
    CamAlarmRepository camAlarmRepository;

    @Autowired
    private ChangelogDao changelogDao;

    @Autowired
    private ImageRecordingValve imageHandler;

    @Autowired
    private AudioHandlerI audioHandler;

    @Autowired
    CamSessionHolder camSessionHolder;

    @Autowired
    private CamParams camParams;

    @Autowired
    private AlarmBroadcaster alarmBroadcaster;

    final private Object sessionDbIdLock = new Object();

    /*
     * This is the "real" connection to the camera; whereas camSessionHolder has a reference to
     * the database entity that represents the connection.
     *
     * We need to overhaul how the lifecycle and access to the two objects is handled, and revisit
     * synchronization of connection logic and getting access to this object.
     */
    private FoscamSession foscamSession;

    @PostConstruct
    public void go()
    {

        log.info( "Database schema version={}.", changelogDao.getSchemaVersion() );

        final LostConnectionHandlerI lostConnectionHandler = new LostConnectionHandlerI()
        {

            @Override
            public void onLostConnection( LostConnectionEvt evt )
            {
                log.warn("LOST CONNECTION to {}!", evt.getSession().getCameraId());
                foscamSession = null;
                connectLoopThread( camParams, this );
            }
        };

        connectLoopThread( camParams, lostConnectionHandler );
    }

    private FoscamSession connect( final CamParams camParams, final LostConnectionHandlerI lostConnectionHandler )
    {
        AlarmHandlerI alarmHandler = new AlarmHandlerI()
        {

            @Override
            public void onAlarm( AlarmEvt evt )
            {
                long now = System.currentTimeMillis();

                    //An alarm may be fired early on during the initial connection, so we need to
                    //make sure that that we have logged the session connection and gotten a
                    //database id before we log the alarm event.
                synchronized ( sessionDbIdLock )
                {
                    log.info("Alarm type={}, sessionDbId={}.", evt.getAlarmNotify().getAlarmType(), camSessionHolder.getCamSession().getId());

                    CamAlarm camAlarm = new CamAlarm(evt.getAlarmNotify().getAlarmType(), new Timestamp(now), camSessionHolder.getCamSession());
                    camAlarmRepository.save(camAlarm);

                    AlarmStatusDto alarmStatusDto = new AlarmStatusDto(evt.getAlarmNotify().getAlarmType(), new Date(now));
                    CamSessionDto oldCamSessionDto = camSessionHolder.getCamSessionDto();
                    CamSessionDto camSessionDto = new CamSessionDto(oldCamSessionDto.getCameraSessionId(), true, oldCamSessionDto.getStreamStatus(), alarmStatusDto);
                    camSessionHolder.setCamSessionDto(camSessionDto);

                    alarmBroadcaster.broadcast(alarmStatusDto);
                }
            }
        };

        String hostname = camParams.getHostname();
        int port = camParams.getPort();
        String username = camParams.getUsername();
        String password = camParams.getPassword();

        FoscamSession session;

        synchronized ( sessionDbIdLock )
        {
            session = FoscamSession.connect( new InetSocketAddress( hostname, port ),
                    username, password, audioHandler, imageHandler, alarmHandler, lostConnectionHandler );

            long now = System.currentTimeMillis();
            CamSession camSession = new CamSession(session.getCameraId(), session.getFirmwareVersion().getMajor(), session.getFirmwareVersion().getMinor(), session.getFirmwareVersion().getPatch(), session.getFirmwareVersion().getBuild(), new Timestamp(now));
            camSession = camSessionRepository.save(camSession);
            camSessionHolder.setCamSession(camSession);
            CamSessionDto camSessionDto = new CamSessionDto(camSession.getId(), true, new StreamStatusDto(false, false), null);
            camSessionHolder.setCamSessionDto(camSessionDto);
        }

        return session;
    }


    private void connectLoop( final CamParams camParams, final LostConnectionHandlerI lostConnectionHandler )
    {
        boolean connected = false;

        while ( ! connected )
        {
            try
            {
                foscamSession = connect(camParams, lostConnectionHandler);
//                foscamSession.videoStart();
//                foscamSession.audioStart();
                connected = true;
            }
            catch ( Exception e )
            {
                final int sleepSecs = 5;
                log.info( "Failed to connect; sleeping " + sleepSecs + " seconds before retry:", e );

                try
                {
                    Thread.sleep( sleepSecs * 1000 );
                }
                catch ( InterruptedException ignored )
                {
                }
            }
        }
    }

    private Thread connectLoopThread( final CamParams camParams, final LostConnectionHandlerI lostConnectionHandler )
    {
        Runnable r = new Runnable()
        {

            @Override
            public void run()
            {
                connectLoop( camParams, lostConnectionHandler );
            }
        };

        r = new DurationLoggingRunnable( r, "session connector" );

        Thread t = new Thread( r, "session connector" );
        t.start();

        return t;
    }

    public boolean isVideoRecordingEnabled() {
        return imageHandler.isRecordingEnabled();
    }

    public void setVideoRecordingEnabled(boolean isVideoRecordingEnabled) {
        imageHandler.setRecordingEnabled(isVideoRecordingEnabled);
    }

    public FoscamSession getFoscamSession() {
        return foscamSession;
    }

}
