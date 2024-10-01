package com.capstonexjapan.line_backend.line_message.service;

import com.linecorp.bot.model.PushMessage;
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

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class MessageCreator {
    private final String to;

    public MessageCreator() {
        this.to = "U3a8d923f58cb7822a6c9ead5c53fe82c";
    }

    // 1. 기본 텍스트 메시지 생성 메서드
    public Mono<PushMessage> createTextMessage(String message) {
        return Mono.just(new PushMessage(to, new TextMessage(message)));
    }

    // 2. 새로운 텍스트 메시지 생성 메서드 (봄 컬렉션 메시지)
    public Mono<PushMessage> createSpringCollectionTextMessage() {
        String message = "이번에 신제품 아이템들이 많이 출시됩니다!\n\n봄을 위한 공식 스커트 컬렉션을 확인하세요. 링크를 클릭하여 자세히 알아보세요!";
        return createTextMessage(message);
    }

    // 3. Flex 메시지를 이용한 상품 카드 메시지 생성 메서드
    public Mono<PushMessage> createProductCardMessage() {
        // 각 상품 카드 생성
        Bubble product1 = createProductCardBubble("엔보스체크 미니스커트", "디자인이 귀여운 미니스커트입니다.", "¥2,340+세금", "https://i.pinimg.com/originals/1b/a1/7c/1ba17c430dec4043fa891739064c1d3c.jpg", "https://i.pinimg.com/originals/1b/a1/7c/1ba17c430dec4043fa891739064c1d3c.jpg");
        Bubble product2 = createProductCardBubble("기본 A라인 스커트", "심플한 디자인의 스커트입니다.", "¥2,450+세금", "https://i.pinimg.com/originals/1c/64/82/1c648277851268bdb3e76f936aeace25.jpg", "https://i.pinimg.com/originals/1c/64/82/1c648277851268bdb3e76f936aeace25.jpg");
        // Add more products as needed

        // Carousel Container 생성
        Carousel carousel = Carousel.builder()
                .contents(asList(product1, product2)) // 여기에 추가된 상품 Bubble을 포함
                .build();

        return Mono.just(new PushMessage(to, new FlexMessage("상품 목록", carousel)));
    }

    // 4. 상품 카드 Bubble 생성 메서드
    private Bubble createProductCardBubble(String title, String description, String price, String imageUrl, String productUrl) {
        return Bubble.builder()
                .hero(Image.builder().url(imageUrl).size(Image.ImageSize.FULL_WIDTH).aspectMode(Image.ImageAspectMode.Cover).build())
                .body(Box.builder()
                        .layout(FlexLayout.VERTICAL)
                        .contents(asList(
                                Text.builder().text(title).weight(Text.TextWeight.BOLD).size(FlexFontSize.Md).build(),
                                Text.builder().text(description).size(FlexFontSize.SM).wrap(true).build(),
                                Text.builder().text(price).size(FlexFontSize.SM).wrap(true).build()
                        ))
                        .build())
                .footer(Box.builder()
                        .layout(FlexLayout.VERTICAL)
                        .contents(Collections.singletonList(
                                Button.builder().style(Button.ButtonStyle.LINK)
                                        .action(new URIAction("자세히 보기", productUrl))
                                        .build()
                        ))
                        .build())
                .build();
    }


    // 기존의 createText2Message 메서드 (텍스트 + 질문 메시지)
    public Mono<PushMessage> createText2Message(String question, String message) {
        final Text titleText = Text.builder()
                .text(message)
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.XL)
                .build();
        final Box questionBox = createQuestionBox(question);

        Box titleAndQuestionBox = Box.builder().layout(FlexLayout.VERTICAL)
                .contents(asList(titleText, questionBox))
                .build();

        Box footerBox = createFooterBlock();

        final Bubble bubble = Bubble.builder()
                .body(titleAndQuestionBox)
                .footer(footerBox)
                .build();

        return Mono.just(new PushMessage(to, new FlexMessage("ALT", bubble)));
    }

    private Box createQuestionBox(String question) {
        final Box place = Box.builder().layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(asList(
                        Text.builder().text("질문")
                                .size(FlexFontSize.SM).flex(1)
                                .build(),
                        Text.builder().text(question).wrap(true)
                                .size(FlexFontSize.SM).flex(5)
                                .build()
                ))
                .build();

        return Box.builder().layout(FlexLayout.VERTICAL)
                .margin(FlexMarginSize.LG)
                .spacing(FlexMarginSize.SM)
                .contents(Collections.singletonList(place))
                .build();
    }

    private Box createFooterBlock() {
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(ButtonsFunction())
                .build();
    }

    private List<FlexComponent> ButtonsFunction() {
        final Separator separator = Separator.builder().build();

        // 버튼 생성
        Function<String, Button> buttonSupplier = item -> Button.builder()
                .style(Button.ButtonStyle.PRIMARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new URIAction(item, "tel:01094298396"))
                .build();

        final Button button1 = buttonSupplier.apply("Button1");
        final Button button2 = buttonSupplier.apply("Button2");
        final Button button3 = buttonSupplier.apply("Button3");
        final Button button4 = buttonSupplier.apply("Button4");
        final Button button5 = buttonSupplier.apply("Button5");

        return asList(button1, separator, button2, separator, button3, separator, button4, separator, button5);
    }
}
