package com.example.client;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.time.Instant;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    RSocket rSocket() {
        return RSocketFactory
                .connect()
                .mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE,MimeTypeUtils.APPLICATION_JSON_VALUE)
                .frameDecoder(PayloadDecoder.ZERO_COPY)
                .transport(TcpClientTransport.create(7000))
                .start()
                .block();
    }

    @Bean
    RSocketRequester requester(RSocketStrategies strategies) throws URISyntaxException {
        return RSocketRequester.builder().rsocketStrategies(strategies)
                .rsocketFactory(factory -> {
                    factory.dataMimeType(MimeTypeUtils.ALL_VALUE)
                            .frameDecoder(PayloadDecoder.ZERO_COPY);
                })
                .connect(TcpClientTransport.create(7000))
                .retry()
                .block();
    }
}

@RestController
class GreetingsRestController {
    private final RSocketRequester requester;

    GreetingsRestController(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping("/greet/{name}")
    Publisher<GreetingsResponse> greet(@PathVariable String name) {
        return requester.route("greet")
                .data(new GreetingsRequest(name))
                .retrieveMono(GreetingsResponse.class);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {
    private String name;
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