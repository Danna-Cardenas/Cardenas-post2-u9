package com.universidad.seguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SeguridadWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeguridadWebApplication.class, args);
    }
}