package com.sibilantsolutions.grison.db.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

//Customize this object's bean name away from default generated name of "config" to avoid conflict with
//org.springframework.boot.autoconfigure.data.rest.SpringBootRepositoryRestMvcConfiguration.config()
@Configuration("myConfig")
public class Config {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    Config() {
        LOG.info("Constructed {}.", getClass());
    }

    @Bean
    File imageFileDir(@Value("${imageFileDir}") File dir) {
        mkdir(dir);

        return dir;
    }

    @Bean
    File audioFileDir(@Value("${audioFileDir}") File dir) {
        mkdir(dir);

        return dir;
    }

    private void mkdir(File dir) {
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                throw new RuntimeException("Failed to create parentDir from file=" + dir);
            }
        }
    }

}
