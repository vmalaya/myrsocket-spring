package com.example.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {
    private String name;
}

@Controller
class GreetingsRSocketController {

    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        return new GreetingsResponse(request.getName());
    }

}

@Data
class GreetingsResponse {
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

