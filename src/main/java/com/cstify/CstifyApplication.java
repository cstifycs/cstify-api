package com.cstify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CstifyApplication {
    static void main(String[] args) {
        SpringApplication.run(CstifyApplication.class, args);
    }
}
