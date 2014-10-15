package com.sibilantsolutions.grison.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sibilantsolutions.iptools.util.DurationLoggingRunnable;

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
                int i = 0;
                String hostname = args[i++];
                int port = Integer.parseInt( args[i++] );
                String username = args[i++];
                String password = args[i++];

                new GrisonDb().start( hostname, port, username, password );
            }
        };

        r = new DurationLoggingRunnable( r, "main" );

        r.run();
    }

    private void start( final String hostname, final int port, final String username,
            final String password )
    {
        ApplicationContext ctx = initSpring();

        DbLogger dbLogger = ctx.getBean( DbLogger.class );

        dbLogger.go( hostname, port, username, password );
    }

    private ApplicationContext initSpring()
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext( "/spring/applicationContext.xml" );

        log.info( "ctx={}.", ctx );

        return ctx;
    }

}
