package com.capstonexjapan.line_backend.line_message.dto;

import lombok.Getter;
import lombok.Setter;

public class MessageDTO {
    @Getter
    @Setter
    public static class TextMessageRequest {
        private String message;
    }

    @Getter
    @Setter
    public static class FlexMessageRequest {
        private String question;
        private String message;
    }
}
