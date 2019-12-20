package com.example.server;

import lombok.Data;

import java.time.Instant;

@Data
public class GreetingsResponse {
    private String greeting;

    GreetingsResponse() {
    }

    GreetingsResponse(String name) {
        this.withGreeting("Hello, " + name + " @ " + Instant.now());
    }

    GreetingsResponse withGreeting(String message) {
        greeting = message;
        return this;
    }
}
