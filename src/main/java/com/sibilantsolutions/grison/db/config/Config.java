package com.sibilantsolutions.grison.db.config;

import com.sibilantsolutions.grison.db.handler.FileWriterParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class Config {

    @Bean
    FileWriterParams imageFileWriterParams(@Value("${imageFileDir}") String dir) {
        return new FileWriterParams(new File(dir));
    }

    @Bean
    FileWriterParams audioFileWriterParams(@Value("${audioFileDir}") String dir) {
        return new FileWriterParams(new File(dir));
    }

}
