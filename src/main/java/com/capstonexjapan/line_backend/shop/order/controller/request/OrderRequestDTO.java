package com.capstonexjapan.line_backend.shop.order.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderRequestDTO {
    private String currency;
    private Integer quantity;
    private String request;
    private String paymentMethod;
    private Long productId;
    private Long userId;
    private Integer deliveryPrice;
    private Long storeId;
}
