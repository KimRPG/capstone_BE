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
                    Request userRequest = new Request(userMessage);
                    String replyToken = messageEvent.getReplyToken();
                    String userId = messageEvent.getSource().getUserId();
                    handleTextMessage(replyToken, userRequest, userId); //
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

    private void handleTextMessage(String replyToken, Request userMessage, String userId) {
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

        String category = getCategoryFromAI(userMessage);
        List<Product> recommendedProducts = ("추천".equals(category)) ? fetchRecommendedProducts() : null;
        handleAIResponse(replyToken, category, userId, recommendedProducts);
    }

    // AI 응답에 따른 메시지 처리
    private void handleAIResponse(String replyToken, String category, String userId, List<Product> recommendedProducts) {
        switch (category) {
            case "추천":
                // 추천일 때: ProductCard 전송
                List<MessageDTO.ProductCardRequest> productList = recommendedProducts.stream().map(product -> {
                    MessageDTO.ProductCardRequest productCard = new MessageDTO.ProductCardRequest();
                    productCard.setTitle(product.getName());
                    productCard.setDescription(product.getDescription());
                    productCard.setPrice("¥" + product.getPrice());
                    productCard.setImageUrl(product.getImageUrl());
                    productCard.setProductUrl("http://172.17.197.2:5174/productview/" + product.getProductId());
                    return productCard;
                }).collect(Collectors.toList());

                messageService.publishProductCardMessage(userId, productList).subscribe();
                break;

            case "배송":
                sendContactButtonMessage(replyToken, "배송 상태가 궁금하신가요?", "배송조회", "http://172.17.197.2:5174/shippingdetail", "+821012345678");
                break;

            case "환불":
                sendContactButtonMessage(replyToken, "환불 정보가 궁금하신가요?", "결제내역", "http://172.17.197.2:5174/orderlist", "+821012345678");
                break;

            default:
                sendContactButtonMessage(replyToken, "문의가 필요하시면 아래 버튼을 눌러주세요: " + category, "도움 요청", "http://172.17.197.2:5174/mypage", "+821012345678");
                break;
        }
    }

    // AI 카테고리 가져오는 메서드 (현승이형이 어떻게 할까...?)
    private String getCategoryFromAI(Request userMessage) {
        aiService.getRecommend(userMessage);

        return "추천"; // 가상값
    }

    private List<Product> fetchRecommendedProducts() {
        // 임의의 제품 목록 반환
        return List.of(
                new Product(4L, "르세핀턱와이드데임팬츠 (3color)", "https://capstone.thewc.co.jp/A/e2636e9b-5894-4e76-88f8-4e833c703daf.jpg", 1800, ProductStatus.AVAILABLE, 100, "#스트릿 #캐주얼", "앤드모어", null, new Date(), new Date(), null),
                new Product(5L, "체크 오버 남방", "https://capstone.thewc.co.jp/A/66de97c8-b0c7-455f-b641-59229a0c5d9f.jpg", 1680, ProductStatus.AVAILABLE, 100, "#심플베이직 #스트릿", "코히", null, new Date(), new Date(), null),
                new Product(6L, "루즈 니트", "https://capstone.thewc.co.jp/A/e5426721-8284-4552-af98-6161a2a0f98a.jpg", 2350, ProductStatus.AVAILABLE, 40, "#심플베이직 #캐주얼", "어바웃영", null, new Date(), new Date(), null),
                new Product(7L, "심볼 로고 맨투맨", "https://capstone.thewc.co.jp/A/03c01263-5df9-4a67-95e2-afe8a4118f7b.jpg", 2790, ProductStatus.AVAILABLE, 40, "#캐주얼브랜드 #4차완판", "로스트리퍼블릭", null, new Date(), new Date(), null),
                new Product(8L, "부츠컷 데님", "https://capstone.thewc.co.jp/A/771fa399-7e83-46eb-bc9f-7471ca203a63.jpg", 2129, ProductStatus.AVAILABLE, 50, "#로맨틱 #심플베이직", "조이조이", null, new Date(), new Date(), null),
                new Product(9L, "오버 니트", "https://capstone.thewc.co.jp/A/3abfb50e-dbc6-4b27-ae76-8c96c8aae93f.jpg", 2880, ProductStatus.AVAILABLE, 30, "#스트릿 #캐주얼", "하이쭈", null, new Date(), new Date(), null)
        );
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
    private void callProductCardApi(String replyToken, String userMessage, String userId) {
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

        List<MessageDTO.ProductCardRequest> productList = Arrays.asList(product1, product2);

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