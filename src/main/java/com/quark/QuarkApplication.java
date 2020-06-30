package com.quark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.quark.mapper.**")
public class QuarkApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuarkApplication.class,args);
    }
}
