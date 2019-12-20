package com.example.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GreetingsRouter {

    @Bean
    RouterFunction<ServerResponse> routes(GreetingHandlers greetingHandlers) {
        return route().GET("/greet/{name}", greetingHandlers::greet)
                .build();
    }
}
