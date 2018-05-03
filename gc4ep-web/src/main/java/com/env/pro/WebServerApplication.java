package com.env.pro;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Created by tim on 22/06/2017.
 */
@SpringBootApplication
//@EnableScheduling
public class WebServerApplication {

    static int port;
    static String host;

    @Autowired
    private Environment env;

    static Logger logger = LoggerFactory.getLogger(WebServerApplication.class);

    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        SpringApplication.run(WebServerApplication.class, args);
        System.out.println("BgServerApplication started. http://" + host + ":" + (port) + ", spend " + (System.currentTimeMillis() - time) / 1000 + "s");
    }

    //@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.info("The beans provided by Spring Boot ");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.info(beanName);
            }
        };
    }


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        String portStr = env.getProperty("server.port", "8011");
        port = Integer.valueOf(portStr);
        host = env.getProperty("server.host", "127.0.0.1");
        factory.setPort(port);

        //factory.addAdditionalTomcatConnectors(initiateHttpConnector());
        return factory;
    }

    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(port-100);
        connector.setSecure(false);
        connector.setRedirectPort(port);
        return connector;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

}
