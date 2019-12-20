package com.example.client;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GreetingHandlers {

    private final RSocketRequester rSocketRequester;

    public Mono<ServerResponse> greet(ServerRequest request) {
        String name = request.pathVariable("name");
        return ServerResponse.ok()
                .body(rSocketRequester.route("greet")
                              .data(new GreetingsRequest(name))
                              .retrieveMono(GreetingsResponse.class), GreetingsResponse.class);
    }
}
