package com.capstonexjapan.line_backend.line_message.service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {
    private final LineMessagingClient lineMessagingClient;
    private final MessageCreator messageCreator;

    public Mono<Void> publishTextMessage(String message) {
        return publishMessage(messageCreator.createTextMessage(message));
    }

    public Mono<Void> publishText2Message(String question, String message) {
        return publishMessage(messageCreator.createText2Message(question, message));
    }

    private Mono<Void> publishMessage(Mono<PushMessage> pushMessageMono) {
        return pushMessageMono
                .flatMap(pushMessage -> Mono.fromCompletionStage(lineMessagingClient.pushMessage(pushMessage)))
                .flatMap(botApiResponse -> Mono.empty());
    }
}