package com.evideo.evideobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EvideobackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EvideobackendApplication.class, args);
    }
}
