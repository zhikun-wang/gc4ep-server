package com.env.pro.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 Created by Zhikun on 20/11/2017. */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public Docket createRestApi() {
//        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
//            @Override
//            public boolean apply(RequestHandler input) {
//                Class<?> declaringClass = input.declaringClass();
//                if (declaringClass == BasicErrorController.class)// 排除
//                    return false;
//                if (declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
//                    return true;
//                if (input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
//                    return true;
//                return false;
//            }
//        };
//        logger.info("swagger started,{}", predicate);
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(predicate)
//                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.env.pro.web"))
                .paths(PathSelectors.any())
                .build();



    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口服务定义")//大标题
                .version("1.0")//版本
                .build();
    }

}
