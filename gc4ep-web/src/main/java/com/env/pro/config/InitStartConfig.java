package com.env.pro.config;

import com.env.pro.util.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 Created by Zhikun on 13/01/2018. */
@Configuration
public class InitStartConfig {

    Logger logger = LoggerFactory.getLogger(InitStartConfig.class);

    @Autowired
    private Environment env;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        int port = 502;
        String portStr = env.getProperty("socket.port");
        if (!StringUtils.isEmpty(portStr)) {
            port = Integer.parseInt(portStr);
        }
        SocketServer.start(port);
        return args -> {

        };
    }
}
