package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.capstonexjapan.line_backend.line_message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/channel/line") // 여기서 경로를 '/channel/line'으로 설정
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 텍스트 메시지 전송을 위한 엔드포인트
    @PostMapping("/text")
    public Mono<ResponseEntity<Void>> sendTextMessage(@RequestBody MessageDTO.TextMessageRequest request) {
        return messageService.publishTextMessage(request.getMessage())
                .thenReturn(ResponseEntity.ok().build());
    }

    // Flex 메시지 전송을 위한 엔드포인트
    @PostMapping("/flex")
    public Mono<ResponseEntity<Void>> sendFlexMessage(@RequestBody MessageDTO.FlexMessageRequest request) {
        return messageService.publishText2Message(request.getQuestion(), request.getMessage())
                .thenReturn(ResponseEntity.ok().build());
    }

    // 요청 객체 정의
}
