package com.mesti.havelange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class HavelangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HavelangeApplication.class, args);
    }

}
