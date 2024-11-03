package com.capstonexjapan.line_backend.line_message.controller;

import com.capstonexjapan.line_backend.line_message.dto.MessageDTO;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class MessageCreator {

    // 기본 텍스트 메시지 생성 메서드
    public Mono<PushMessage> createTextMessage(String userId, String message) {
        return Mono.just(new PushMessage(userId, new TextMessage(message)));
    }

    // 다수의 상품 카드 메시지 생성 메서드 (Carousel 사용)
    public Mono<PushMessage> createProductCards(String userId, List<MessageDTO.ProductCardRequest> productCards) {
        List<Bubble> bubbles = productCards.stream()
                .map(product -> createProductCardBubble(product.getTitle(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getProductUrl()))
                .toList();

        Carousel carousel = Carousel.builder().contents(bubbles).build();
        String altText = "상품 목록을 확인하세요.";

        return Mono.just(new PushMessage(userId, new FlexMessage(altText, carousel)));
    }

    // 단일 상품 카드 Bubble 생성 메서드
    private Bubble createProductCardBubble(String title, String description, String price, String imageUrl, String productUrl) {
        return Bubble.builder()
                .hero(Image.builder().url(imageUrl).size(Image.ImageSize.FULL_WIDTH).aspectMode(Image.ImageAspectMode.Cover).build())
                .body(Box.builder().layout(FlexLayout.VERTICAL).contents(asList(
                        Text.builder().text(title).weight(Text.TextWeight.BOLD).size(FlexFontSize.Md).build(),
                        Text.builder().text(description).size(FlexFontSize.SM).wrap(true).build(),
                        Text.builder().text(price).size(FlexFontSize.SM).wrap(true).build()
                )).build())
                .footer(Box.builder().layout(FlexLayout.VERTICAL).contents(asList(
                        Box.builder().layout(FlexLayout.HORIZONTAL).contents(asList(
                                Button.builder().style(Button.ButtonStyle.LINK).action(new URIAction("자세히 보기", productUrl)).build()
                        )).build()
                )).build())
                .build();
    }

    // 텍스트 + 버튼 메시지 생성 메서드
    public Mono<PushMessage> createText2Message(String userId, String message, String question, List<Button> buttons) {
        Text titleText = Text.builder()
                .text(message)
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.XL)
                .build();

        Text questionText = Text.builder()
                .text(question)
                .weight(Text.TextWeight.REGULAR)
                .size(FlexFontSize.SM)
                .color("#808080").build();

        Box bodyBox = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(List.of(titleText, questionText))
                .build();

        Box footerBox = createFooterBlock(buttons);

        Bubble bubble = Bubble.builder().body(bodyBox).footer(footerBox).build();
        FlexMessage flexMessage = new FlexMessage("질문과 답변 메시지", bubble);

        return Mono.just(new PushMessage(userId, flexMessage));
    }

    private Box createFooterBlock(List<Button> buttons) {
        // 각 버튼을 Box로 감싸서 FlexComponent로 변환
        List<FlexComponent> buttonComponents = buttons.stream()
                .map(button -> Box.builder()
                        .layout(FlexLayout.HORIZONTAL)
                        .contents(asList(button))  // 버튼을 FlexComponent로 변환
                        .build()
                ).collect(Collectors.toList());

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(buttonComponents)  // 버튼 리스트를 FlexComponent 리스트로 설정
                .build();
    }

    // 버튼 생성 메서드
    public Button createButtonFromRequest(MessageDTO.ButtonActionRequest buttonRequest) {
        switch (buttonRequest.getActionType().toLowerCase()) {
            case "uri":
                return Button.builder()
                        .style(Button.ButtonStyle.PRIMARY)
                        .color("#FF8C8C")
                        .height(Button.ButtonHeight.SMALL)
                        .action(new URIAction(buttonRequest.getLabel(), buttonRequest.getActionData()))
                        .build();
            case "message":
                return Button.builder()
                        .style(Button.ButtonStyle.PRIMARY)
                        .color("#FF8C8C")
                        .height(Button.ButtonHeight.SMALL)
                        .action(new PostbackAction(buttonRequest.getLabel(), buttonRequest.getActionData()))
                        .build();
            default:
                throw new IllegalArgumentException("Unsupported action type: " + buttonRequest.getActionType());
        }
    }
}
