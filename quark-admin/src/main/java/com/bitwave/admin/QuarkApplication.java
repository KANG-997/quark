package com.bitwave.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bitwave.**")
public class QuarkApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuarkApplication.class,args);
    }
}
