package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.ai.controller.response.Request;
import com.capstonexjapan.line_backend.ai.service.AIService;
import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.capstonexjapan.line_backend.line_message.service.MessageService;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
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
    private final MessageController messageController;
    private final AIService aiService;

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
                    String userId = messageEvent.getSource().getUserId();
                    handleTextMessage(replyToken, userMessage, userId); //
                }
            } else if (event instanceof PostbackEvent) { // Postback 이벤트 처리
                PostbackEvent postbackEvent = (PostbackEvent) event;
                String postbackData = postbackEvent.getPostbackContent().getData();
                String replyToken = postbackEvent.getReplyToken();
                String userId = postbackEvent.getSource().getUserId();
                handlePostbackEvent(replyToken, postbackData, userId);
            }
        });

        return ResponseEntity.ok("Webhook handled");
    }

    private void handleTextMessage(String replyToken, String userMessage, String userId) {
        // 받은 메시지에 따른 API 호출 로직
//        switch (userMessage) {
//            case "기본":
//                callTextApi(replyToken, userMessage, userId);
//                break;
//            case "버튼":
//                callQuestionApi(replyToken, userMessage, userId);
//                break;
//            case "상품":
//                callProductCardApi(replyToken, userMessage, userId);
//                break;
//            default:
//                // 기본 응답
//                TextMessage replyMessage = new TextMessage("You said: " + userMessage);
//                ReplyMessage reply = new ReplyMessage(replyToken, replyMessage);
//                try {
//                    lineMessagingClient.replyMessage(reply).get();
//                } catch (InterruptedException | ExecutionException e) {
//                    log.error("Failed to reply to message", e);
//                }
//                break;
//        }

        float[] embedding = embedding(userMessage);
        String category = getCategoryFromAI(embedding);
        List<Product> recommendedProducts = ("추천".equals(category)) ? aiService.getRecommendProduct(embedding) : null;
        handleAIResponse(replyToken, category, userId, recommendedProducts);
    }

    // AI 응답에 따른 메시지 처리
    private void handleAIResponse(String replyToken, String category, String userId, List<Product> recommendedProducts) {
        switch (category) {
            case "추천":
                // 추천일 때: ProductCard 전송
                List<MessageDTO.ProductCardRequest> productList = recommendedProducts.stream().map(MessageDTO.ProductCardRequest::toDto).collect(Collectors.toList());

                messageService.publishProductCardMessage(userId, productList).subscribe();
                break;

            case "배송":
                sendContactButtonMessage(replyToken, "배송 상태가 궁금하신가요?", "배송조회", "http://172.17.197.2:5173/shippingdetail", "+821012345678");
                break;

            case "환불":
                sendContactButtonMessage(replyToken, "환불 정보가 궁금하신가요?", "결제내역", "http://172.17.197.2:5173/orderlist", "+821012345678");
                break;

            default:
                sendContactButtonMessage(replyToken, "문의가 필요하시면 아래 버튼을 눌러주세요: " + category, "도움 요청", "http://172.17.197.2:5173/mypage", "+821012345678");
                break;
        }
    }

    private float[] embedding(String userMessage) {
        return aiService.embedding(userMessage);
    }

    // AI 카테고리 가져오는 메서드 (현승이형이 어떻게 할까...?)
    private String getCategoryFromAI(float[] embedding) {

        return aiService.getRecommend(embedding); // 가상값
    }

//    private List<Product> fetchRecommendedProducts() {
//        // 임의의 제품 목록 반환
//    }

    // PostbackEvent 처리 메서드
    private void handlePostbackEvent(String replyToken, String postbackData, String userId) {
        if ("상품".equals(postbackData)) {
            callProductCardApi(userId); // Postback이 "상품"인 경우
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
//    private void callTextApi(String replyToken, String userMessage, String userId) {
//        MessageDTO.TextMessageRequest request = new MessageDTO.TextMessageRequest();
//        request.setMessage("일반 자동 응답 텍스트");
//        request.setUserId(userId);
//
//        messageService.publishTextMessage(request.getUserId(), request.getMessage()).subscribe();
//    }

    // 버튼 메시지 API 호출
//    private void callQuestionApi(String replyToken, String userMessage, String userId) {
//        // FlexMessageRequest 객체 생성 및 설정
//        MessageDTO.FlexMessageRequest request = new MessageDTO.FlexMessageRequest();
//        request.setUserId(userId);
//        request.setMessage("배송비를 확인해 주세요.");
//        request.setQuestion("이 상품은 얼마인가요?");
//
//        // ButtonActionRequest 객체 생성 및 설정
//        MessageDTO.ButtonActionRequest button1 = new MessageDTO.ButtonActionRequest();
//        button1.setLabel("자세히 보기");
//        button1.setActionType("uri");
//        button1.setActionData("https://example.com");
//
//        MessageDTO.ButtonActionRequest button2 = new MessageDTO.ButtonActionRequest();
//        button2.setLabel("상품 보기");
//        button2.setActionType("message");
//        button2.setActionData("상품");
//
//        // 버튼 리스트 생성
//        List<MessageDTO.ButtonActionRequest> buttonActionRequests = Arrays.asList(button1, button2);
//        request.setButtons(buttonActionRequests);
//
//        MessageCreator messageCreator = new MessageCreator();
//        List<Button> buttons = buttonActionRequests.stream()
//                .map(messageCreator::createButtonFromRequest)
//                .collect(Collectors.toList());
//
//        messageService.publishText2Message(request.getUserId(), request.getMessage(), request.getQuestion(), buttons).subscribe();
//    }

    // 상품 카드 메시지 API 호출
    private void callProductCardApi( String userId) {


        List<MessageDTO.ProductCardRequest> productList = Arrays.asList();

        messageService.publishProductCardMessage(userId, productList).subscribe();
    }

    // 전화 버튼 메시지 전송 메서드 추가
    private void sendContactButtonMessage(String replyToken, String messageText, String buttonLabel, String url, String phoneNumber) {
        TextMessage contactMessage = new TextMessage(messageText);

        Button actionButton = Button.builder()
                .style(Button.ButtonStyle.LINK)
                .action(new URIAction(buttonLabel, url))
                .build();

        Button contactButton = Button.builder()
                .style(Button.ButtonStyle.LINK)
                .action(new URIAction("상담원 연결", "tel:" + phoneNumber))
                .build();

        FlexMessage flexMessage = new FlexMessage("문의 메시지", Bubble.builder()
                .body(Box.builder().layout(FlexLayout.VERTICAL).contents(List.of(
                        Text.builder().text(contactMessage.getText()).size(FlexFontSize.SM).wrap(true).build()
                )).build())
                .footer(Box.builder().layout(FlexLayout.VERTICAL).contents(List.of(actionButton, contactButton)).build())
                .build());

        lineMessagingClient.replyMessage(new ReplyMessage(replyToken, flexMessage));
    }
}