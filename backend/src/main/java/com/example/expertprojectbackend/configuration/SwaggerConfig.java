package com.example.expertprojectbackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class for Swagger documentation.
 */
@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    /**
     * Creates a Docket bean for Swagger API documentation.
     *
     * @return Docket object for Swagger configuration
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.expertprojectbackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}