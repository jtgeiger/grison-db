package com.sibilantsolutions.grison.db.config;

import com.sibilantsolutions.grison.db.handler.FileWriterParams;
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
    FileWriterParams imageFileWriterParams(@Value("${imageFileDir}") String dir) {
        return new FileWriterParams(new File(dir));
    }

    @Bean
    FileWriterParams audioFileWriterParams(@Value("${audioFileDir}") String dir) {
        return new FileWriterParams(new File(dir));
    }

}
