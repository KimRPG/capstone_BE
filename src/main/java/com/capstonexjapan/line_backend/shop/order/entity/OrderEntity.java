package com.capstonexjapan.line_backend.shop.order.entity;

import com.capstonexjapan.line_backend.shop.order.controller.request.OrderRequestDTO;
import com.capstonexjapan.line_backend.shop.order.controller.request.UpdateOrderDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String currency;

    private Integer quantity;

    private String request;

    private String orderStatus; //enum

    private String paymentMethod;

    private Long productId;

    @CreationTimestamp
    private Date createTime;

    private Long userId;

    private Integer deliveryPrice;

    private Long storeId;

    public OrderEntity toEntity( OrderRequestDTO dto) {
        return OrderEntity.builder()
                .currency(dto.getCurrency())
                .quantity(dto.getQuantity())
                .request(dto.getRequest())
                .orderStatus("주문 확인 중")
                .paymentMethod(dto.getPaymentMethod())
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .deliveryPrice(dto.getDeliveryPrice())
                .storeId(dto.getStoreId())
                .build();
    }

    public void updateOrder(UpdateOrderDTO dto) {
        if (dto.getCurrency() != null) this.currency = dto.getCurrency();
        if (dto.getQuantity() != null) this.quantity = dto.getQuantity();
        if (dto.getRequest() != null) this.request = dto.getRequest();
        if (dto.getPaymentMethod() != null) this.paymentMethod = dto.getPaymentMethod();
        if (dto.getOrderStatus() != null) this.orderStatus = dto.getOrderStatus();
        if (dto.getDeliveryPrice() != null) this.deliveryPrice = dto.getDeliveryPrice();
        if (dto.getStoreId() != null) this.storeId = dto.getStoreId();
    }
}
