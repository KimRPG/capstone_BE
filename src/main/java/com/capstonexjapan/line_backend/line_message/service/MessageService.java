package com.capstonexjapan.line_backend.line_message.service;

import com.capstonexjapan.line_backend.line_message.controller.MessageCreator;
import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.flex.component.Button;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {

    private final LineMessagingClient lineMessagingClient;
    private final MessageCreator messageCreator;

    public Mono<Void> publishTextMessage(String userId, String message) {
        return publishMessage(messageCreator.createTextMessage(userId, message));
    }

    public Mono<Void> publishText2Message(String userId, String message, String question, List<Button> buttons) {
        return publishMessage(messageCreator.createText2Message(userId, message, question, buttons));
    }

    public Mono<Void> publishProductCardMessage(String userId, List<MessageDTO.ProductCardRequest> productCards) {
        return publishMessage(messageCreator.createProductCards(userId, productCards));
    }

    private Mono<Void> publishMessage(Mono<PushMessage> pushMessageMono) {
        return pushMessageMono
                .flatMap(pushMessage -> Mono.fromCompletionStage(lineMessagingClient.pushMessage(pushMessage)))
                .then();
    }
}
