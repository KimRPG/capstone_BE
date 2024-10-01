package com.capstonexjapan.line_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LineBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LineBackendApplication.class, args);
    }

}
