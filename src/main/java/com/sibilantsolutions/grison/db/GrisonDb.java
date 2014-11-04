package com.sibilantsolutions.grison.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sibilantsolutions.grison.db.domain.CamParams;
import com.sibilantsolutions.utils.util.DurationLoggingRunnable;

public class GrisonDb
{

    final static private Logger log = LoggerFactory.getLogger( GrisonDb.class );

    static public void main( final String[] args )
    {
        Runnable r = new Runnable()
        {

            @Override
            public void run()
            {
                new GrisonDb().start();
            }
        };

        r = new DurationLoggingRunnable( r, "main" );

        r.run();
    }

    private void start()
    {
        ApplicationContext ctx = initSpring();

        DbLogger dbLogger = ctx.getBean( DbLogger.class );
        CamParams camParams = ctx.getBean( CamParams.class );

        dbLogger.go( camParams );
    }

    private ApplicationContext initSpring()
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext( "/spring/applicationContext.xml" );

        log.info( "ctx={}.", ctx );

        return ctx;
    }

}
