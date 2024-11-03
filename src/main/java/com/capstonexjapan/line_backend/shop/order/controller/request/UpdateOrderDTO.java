package com.capstonexjapan.line_backend.shop.order.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderDTO {
    private String currency;
    private Integer quantity;
    private String request;
    private String paymentMethod;
    private String orderStatus;
    private Integer deliveryPrice;
    private Long storeId;
}