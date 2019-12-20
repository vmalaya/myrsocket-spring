package com.example.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingsRSocketController {

    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        return new GreetingsResponse(request.getName());
    }

}
