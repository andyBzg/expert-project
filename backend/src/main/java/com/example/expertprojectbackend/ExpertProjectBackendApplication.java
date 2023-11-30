package com.example.expertprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource("classpath:messages/en/messages.properties")
@EnableScheduling
@SpringBootApplication
public class ExpertProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpertProjectBackendApplication.class, args);
    }

}
