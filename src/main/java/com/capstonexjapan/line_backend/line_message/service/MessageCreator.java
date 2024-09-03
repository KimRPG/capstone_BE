package com.capstonexjapan.line_backend.line_message.service;

import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
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

    public Mono<PushMessage> createTextMessage(String message) {
        return Mono.just(new PushMessage(to, new TextMessage(message)));
    }

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
        // TODO URIAction to PostbackAction
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(ButtonsFunction())
                .build();
    }

    private List<FlexComponent> ButtonsFunction() {
        final Separator separator = Separator.builder().build();

        // Using a supplier function that generates buttons with dynamic labels
        Function<String, Button> buttonSupplier = item -> Button.builder()
                .style(Button.ButtonStyle.PRIMARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new URIAction(item, "tel:01094298396")) // Use item as the label
                .build();

        // Create buttons with labels like "Button1", "Button2", etc.
        final Button button1 = buttonSupplier.apply("Button1");
        final Button button2 = buttonSupplier.apply("Button2");
        final Button button3 = buttonSupplier.apply("Button3");
        final Button button4 = buttonSupplier.apply("Button4");
        final Button button5 = buttonSupplier.apply("Button5");

        // Return the list of buttons with separators
        return asList(button1, separator, button2, separator, button3, separator, button4, separator, button5);
    }
}