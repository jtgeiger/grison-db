package com.sibilantsolutions.grison.db;

import java.net.InetSocketAddress;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sibilantsolutions.grison.db.dao.CamDao;
import com.sibilantsolutions.grison.db.dao.ChangelogDao;
import com.sibilantsolutions.grison.db.domain.CamParams;
import com.sibilantsolutions.grison.db.handler.SessionDbIdHolderI;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionEvt;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;
import com.sibilantsolutions.utils.util.DurationLoggingRunnable;

@Component
public class DbLogger
{

    final static private Logger log = LoggerFactory.getLogger( DbLogger.class );

    @Autowired
    private CamDao camDao;

    @Autowired
    private ChangelogDao changelogDao;

    @Autowired
    private ImageHandlerI imageHandler;

    @Autowired
    private SessionDbIdHolderI sessionDbIdHolder;


    final private Object sessionDbIdLock = new Object();

    public void go( final CamParams camParams )
    {

        log.info( "Database schema version={}.", changelogDao.getSchemaVersion() );

        final LostConnectionHandlerI lostConnectionHandler = new LostConnectionHandlerI()
        {

            @Override
            public void onLostConnection( LostConnectionEvt evt )
            {
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
                    log.info( "Alarm type={}, sessionDbId={}.", evt.getAlarmNotify().getAlarmType(), sessionDbIdHolder.getSessionDbId() );

                    camDao.alarm( evt.getAlarmNotify().getAlarmType(), new Timestamp( now ), sessionDbIdHolder.getSessionDbId() );
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
                    username, password, null, imageHandler, alarmHandler, lostConnectionHandler );

            if ( session != null )
            {
                long now = System.currentTimeMillis();
                Number sessionDbId = camDao.sessionConnected( session.getCameraId(), session.getFirmwareVersion(), new Timestamp( now ) );
                sessionDbIdHolder.setSessionDbId(sessionDbId);
            }
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
                FoscamSession session = connect( camParams, lostConnectionHandler );
                session.videoStart();
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

}
