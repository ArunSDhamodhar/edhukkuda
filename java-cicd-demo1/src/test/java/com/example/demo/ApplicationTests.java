package com.example.demo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest

public class ApplicationTests {


@Test

public void testMessage() {

Application app = new Application();

assertEquals("Hello, CI/CD Pipeline!", app.getMessage());

}

}

