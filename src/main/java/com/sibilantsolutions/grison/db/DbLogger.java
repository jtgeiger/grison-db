package com.sibilantsolutions.grison.db;

import java.net.InetSocketAddress;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sibilantsolutions.grison.db.dao.CamDao;
import com.sibilantsolutions.grison.driver.foscam.net.FoscamSession;
import com.sibilantsolutions.grison.evt.AlarmEvt;
import com.sibilantsolutions.grison.evt.AlarmHandlerI;
import com.sibilantsolutions.grison.evt.LostConnectionHandlerI;

@Component
public class DbLogger
{

    final static private Logger log = LoggerFactory.getLogger( DbLogger.class );

    @Autowired
    private CamDao camDao;

    private Number sessionDbId;
    final private Object sessionDbIdLock = new Object();

    public void go( final String hostname, final int port, final String username,
            final String password )
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
                    log.info( "Alarm type={}, sessionDbId={}.", evt.getAlarmNotify().getAlarmType(), sessionDbId );

                    camDao.alarm( evt.getAlarmNotify().getAlarmType(), new Timestamp( now ), sessionDbId );
                }
            }
        };

        LostConnectionHandlerI lostConnectionHandler = null;    //TODO

        synchronized ( sessionDbIdLock )
        {
            FoscamSession session = FoscamSession.connect( new InetSocketAddress( hostname, port ),
                    username, password, null, null, alarmHandler, lostConnectionHandler );

            if ( session != null )
            {
                long now = System.currentTimeMillis();
                sessionDbId = camDao.sessionConnected( session.getCameraId(), session.getFirmwareVersion(), new Timestamp( now ) );
            }

        }

    }

}
