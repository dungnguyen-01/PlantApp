package com.kinto.kintobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.kinto.kintobackend"})
public class KintoBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(KintoBackendApplication.class, args);
    }

}
