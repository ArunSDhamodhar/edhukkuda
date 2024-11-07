package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    // This method should be public and annotated with @GetMapping to be accessible via HTTP
    @GetMapping("/")
    public String home() {
        return getMessage();
    }

    // This method can remain as is, but it's better to annotate it if it's intended for HTTP access
    public String getMessage() {
        return "Hello, CI/CD Pipeline!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
