package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.capstonexjapan.line_backend.line_message.service.MessageService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Button;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/line")
public class LineWebhookController {

    private final LineMessagingClient lineMessagingClient;
    private final MessageService messageService;
    private final MessageController messageController; // MessageController 주입

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody CallbackRequest callbackRequest) {
        log.info("Webhook received: {}", callbackRequest);

        // 메시지 이벤트 및 포스트백 이벤트 처리
        callbackRequest.getEvents().forEach(event -> {
            if (event instanceof MessageEvent) {
                MessageEvent messageEvent = (MessageEvent) event;
                if (messageEvent.getMessage() instanceof TextMessageContent) {
                    String userMessage = ((TextMessageContent) messageEvent.getMessage()).getText();
                    String replyToken = messageEvent.getReplyToken();
                    String userId = messageEvent.getSource().getUserId();  // 유저 ID 가져오기
                    handleTextMessage(replyToken, userMessage, userId);
                }
            } else if (event instanceof PostbackEvent) { // Postback 이벤트 처리
                PostbackEvent postbackEvent = (PostbackEvent) event;
                String postbackData = postbackEvent.getPostbackContent().getData();
                String replyToken = postbackEvent.getReplyToken();
                String userId = postbackEvent.getSource().getUserId();
                handlePostbackEvent(replyToken, postbackData, userId);
            }
        });

        // 200 OK 반환하여 웹훅 처리 성공을 알림
        return ResponseEntity.ok("Webhook handled");
    }

    private void handleTextMessage(String replyToken, String userMessage, String userId) {
        // 받은 메시지에 따른 API 호출 로직
        switch (userMessage) {
            case "기본":
                callTextApi(replyToken, userMessage, userId);
                break;
            case "버튼":
                callQuestionApi(replyToken, userMessage, userId);
                break;
            case "상품":
                callProductCardApi(replyToken, userMessage, userId);
                break;
            default:
                // 기본 응답
                TextMessage replyMessage = new TextMessage("You said: " + userMessage);
                ReplyMessage reply = new ReplyMessage(replyToken, replyMessage);
                try {
                    lineMessagingClient.replyMessage(reply).get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Failed to reply to message", e);
                }
                break;
        }
    }

    // PostbackEvent 처리 메서드
    private void handlePostbackEvent(String replyToken, String postbackData, String userId) {
        if ("상품".equals(postbackData)) {
            callProductCardApi(replyToken, postbackData, userId); // Postback이 "상품"인 경우
        } else {
            // 다른 Postback 처리 로직
            TextMessage replyMessage = new TextMessage("Postback received: " + postbackData);
            ReplyMessage reply = new ReplyMessage(replyToken, replyMessage);
            try {
                lineMessagingClient.replyMessage(reply).get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Failed to reply to postback", e);
            }
        }
    }

    // 기본 텍스트 메시지 API 호출
    private void callTextApi(String replyToken, String userMessage, String userId) {
        MessageDTO.TextMessageRequest request = new MessageDTO.TextMessageRequest();
        request.setMessage("일반 자동 응답 텍스트");
        request.setUserId(userId);  // userId 설정

        messageService.publishTextMessage(request.getUserId(), request.getMessage()).subscribe();
    }

    // 버튼 메시지 API 호출
    private void callQuestionApi(String replyToken, String userMessage, String userId) {
        // FlexMessageRequest 객체 생성 및 설정
        MessageDTO.FlexMessageRequest request = new MessageDTO.FlexMessageRequest();
        request.setUserId(userId);
        request.setMessage("배송비를 확인해 주세요.");
        request.setQuestion("이 상품은 얼마인가요?");

        // ButtonActionRequest 객체 생성 및 설정
        MessageDTO.ButtonActionRequest button1 = new MessageDTO.ButtonActionRequest();
        button1.setLabel("자세히 보기");
        button1.setActionType("uri");
        button1.setActionData("https://example.com");

        MessageDTO.ButtonActionRequest button2 = new MessageDTO.ButtonActionRequest();
        button2.setLabel("상품 보기");
        button2.setActionType("message");
        button2.setActionData("상품");  // 상품이라는 메시지 전송

        // 버튼 리스트 생성
        List<MessageDTO.ButtonActionRequest> buttonActionRequests = Arrays.asList(button1, button2);
        request.setButtons(buttonActionRequests);

        // ButtonActionRequest -> Button 변환, MessageController의 메서드 사용
        MessageCreator messageCreator = new MessageCreator();
        List<Button> buttons = buttonActionRequests.stream()
                .map(messageCreator::createButtonFromRequest) // MessageController의 메서드를 사용
                .collect(Collectors.toList());

        // FlexMessage 전송
        messageService.publishText2Message(request.getUserId(), request.getMessage(), request.getQuestion(), buttons).subscribe();
    }

    // 상품 카드 메시지 API 호출
    private void callProductCardApi(String replyToken, String userMessage, String userId) {
        // 여러 개의 ProductCardRequest 객체 생성
        MessageDTO.ProductCardRequest product1 = new MessageDTO.ProductCardRequest();
        product1.setTitle("엔보스체크 미니스커트");
        product1.setDescription("디자인이 귀여운 미니스커트입니다.");
        product1.setPrice("¥2,340+세금");
        product1.setImageUrl("https://cf.product-image.s.zigzag.kr/original/c/14/814/588/148145880-1518185832611658513.gif?width=720&height=720&quality=80&format=jpeg");
        product1.setProductUrl("https://www.zigzag.kr/catalog/products/148145880");

        MessageDTO.ProductCardRequest product2 = new MessageDTO.ProductCardRequest();
        product2.setTitle("기본 A라인 스커트");
        product2.setDescription("심플한 디자인의 스커트입니다.");
        product2.setPrice("¥2,450+세금");
        product2.setImageUrl("https://ardormonday.com/web/product/big/202401/4e60938b1db6d796e710598524672edf.jpg");
        product2.setProductUrl("https://ardormonday.com/product/%EA%B8%B0%EB%B3%B8%ED%85%9C%F0%9F%92%9C%ED%97%88%EB%A6%AC%EB%B0%B4%EB%94%A9-%ED%97%88%EB%B0%8D-%EC%97%90%EC%9D%B4%EB%9D%BC%EC%9D%B8-%EC%8A%A4%EC%BB%A4%ED%8A%B8-%EB%92%B7%EB%B0%B4%EB%94%A9-%EC%97%90%EC%9D%B4%ED%95%8F-%EB%AA%A8%EC%A7%81%EC%8A%A4%EC%BB%A4%ED%8A%B8-%EA%B8%B0%EB%B3%B8-%EB%B2%A0%EC%9D%B4%EC%A7%81-%EB%AC%B4%EC%A7%80%EC%B9%98%EB%A7%88/4238/");

        // ProductCardRequest 리스트 생성
        List<MessageDTO.ProductCardRequest> productList = Arrays.asList(product1, product2);

        // MessageService를 통해 productList 전송
        messageService.publishProductCardMessage(userId, productList).subscribe();
    }
}