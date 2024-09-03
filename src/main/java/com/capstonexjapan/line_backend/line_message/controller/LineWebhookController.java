package com.capstonexjapan.line_backend.line_message.controller;

import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/line")
public class LineWebhookController {

    private final LineMessagingClient lineMessagingClient;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody CallbackRequest callbackRequest) {
        log.info("Webhook received: {}", callbackRequest);

        // 메시지 이벤트 처리
        callbackRequest.getEvents().forEach(event -> {
            if (event instanceof MessageEvent) {
                MessageEvent messageEvent = (MessageEvent) event;
                if (messageEvent.getMessage() instanceof TextMessageContent) {
                    String userMessage = ((TextMessageContent) messageEvent.getMessage()).getText();
                    String replyToken = messageEvent.getReplyToken();
                    handleTextMessage(replyToken, userMessage);
                }
            }
        });

        // 200 OK 반환하여 웹훅 처리 성공을 알림
        return ResponseEntity.ok("Webhook handled");
    }

    private void handleTextMessage(String replyToken, String userMessage) {
        // 받은 메시지에 대한 응답 생성
        TextMessage replyMessage = new TextMessage("You said: " + userMessage);
        ReplyMessage reply = new ReplyMessage(replyToken, replyMessage);

        try {
            lineMessagingClient.replyMessage(reply).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Failed to reply to message", e);
        }
    }
}
