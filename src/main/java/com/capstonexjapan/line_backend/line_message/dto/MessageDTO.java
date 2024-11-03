package com.capstonexjapan.line_backend.line_message.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

public class MessageDTO {

    @Getter
    @Setter
    public static class TextMessageRequest {
        private String userId;
        private String message;
    }

    @Getter
    @Setter
    public static class FlexMessageRequest {
        private String userId;
        private String question;
        private String message;
        private List<ButtonActionRequest> buttons;  // 버튼 리스트 추가
    }

    @Getter
    @Setter
    public static class ButtonActionRequest {  // 버튼 액션 정보
        private String label;
        private String actionType;  // URI or message
        private String actionData;  // URL or message content
    }

    @Getter
    @Setter
    public static class ProductCardRequest {
        private String title;       // 상품 제목
        private String description; // 상품 설명
        private String price;       // 상품 가격
        private String imageUrl;    // 상품 이미지 URL
        private String productUrl;  // 상품 링크 URL
    }

    // 여러 개의 ProductCardRequest를 담을 수 있는 클래스 추가
    @Getter
    @Setter
    public static class ProductCardsRequest {
        private String userId;
        private List<ProductCardRequest> productCards; // 상품 카드 리스트
    }
}
