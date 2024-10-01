package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.line_message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/channel/line") // 경로를 '/channel/line'으로 설정
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 텍스트 메시지 전송을 위한 엔드포인트
    @PostMapping("/text")
    public Mono<ResponseEntity<Void>> sendTextMessage(@RequestBody String message) {
        return messageService.publishTextMessage(message)
                .thenReturn(ResponseEntity.ok().build());
    }

    // Flex 메시지 전송을 위한 엔드포인트
    @PostMapping("/flex")
    public Mono<ResponseEntity<Void>> sendFlexMessage(@RequestBody String question, String message) {
        return messageService.publishText2Message(question, message)
                .thenReturn(ResponseEntity.ok().build());
    }

    // 컬렉션 텍스트 메시지 전송을 위한 엔드포인트
    @PostMapping("/product-collection")
    public Mono<ResponseEntity<Void>> sendProductCollectionMessage() {
        return messageService.publishProductCollectionTextMessage()
                .thenReturn(ResponseEntity.ok().build());
    }

    // 상품 카드 Flex 메시지 전송을 위한 엔드포인트
    @PostMapping("/product-card")
    public Mono<ResponseEntity<Void>> sendProductCardMessage() {
        return messageService.publishProductCardMessage()
                .thenReturn(ResponseEntity.ok().build());
    }
}
