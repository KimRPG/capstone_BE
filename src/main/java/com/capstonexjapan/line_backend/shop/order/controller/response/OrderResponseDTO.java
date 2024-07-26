package com.capstonexjapan.line_backend.shop.order.controller.response;

import com.capstonexjapan.line_backend.shop.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderResponseDTO {

    private Long orderId;

    private String currency;

    private Integer quantity;

    private String request;

    private String orderStatus;

    private String paymentMethod;

    private Long productId;

    private Date createTime;
    public OrderResponseDTO toDTO(OrderEntity order) {
        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .currency(order.getCurrency())
                .quantity(order.getQuantity())
                .request(order.getRequest())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .productId(order.getProductId())
                .createTime(order.getCreateTime())
                .build();
    }
}
