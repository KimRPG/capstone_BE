package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.capstonexjapan.line_backend.line_message.service.MessageService;
import com.linecorp.bot.model.message.flex.component.Button;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channel/line")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/text")
    public Mono<ResponseEntity<Void>> sendTextMessage(@RequestBody MessageDTO.TextMessageRequest request) {
        return messageService.publishTextMessage(request.getUserId(), request.getMessage())
                .thenReturn(ResponseEntity.ok().build());
    }

    @PostMapping("/question")
    public Mono<ResponseEntity<Void>> sendFlexMessage(@RequestBody MessageDTO.FlexMessageRequest request) {
        // 버튼들을 request body에서 받아서 처리
        MessageCreator messageCreator = new MessageCreator();
        List<Button> buttons = request.getButtons().stream()
                .map(messageCreator::createButtonFromRequest)
                .collect(Collectors.toList());

        return messageService.publishText2Message(request.getUserId(), request.getMessage(), request.getQuestion(), buttons)
                .thenReturn(ResponseEntity.ok().build());
    }



    @PostMapping("/product-card")
    public Mono<ResponseEntity<Void>> sendProductCardMessage(@RequestBody MessageDTO.ProductCardsRequest request) {
        return messageService.publishProductCardMessage(request.getUserId(), request.getProductCards())
                .thenReturn(ResponseEntity.ok().build());
    }
}
