package com.sibilantsolutions.grison.db.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cam")
public class CamParams
{

    private String hostname;
    private int port;
    private String username;
    private String password;

    public String getHostname()
    {
        return hostname;
    }
    public void setHostname( String hostname )
    {
        this.hostname = hostname;
    }

    public int getPort()
    {
        return port;
    }
    public void setPort( int port )
    {
        this.port = port;
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword( String password )
    {
        this.password = password;
    }

}
