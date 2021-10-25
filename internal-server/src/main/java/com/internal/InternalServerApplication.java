package com.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.core"})
public class InternalServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternalServerApplication.class, args);
    }
}
