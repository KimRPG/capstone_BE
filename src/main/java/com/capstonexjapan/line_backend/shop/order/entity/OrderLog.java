package com.capstonexjapan.line_backend.shop.order.entity;

import com.capstonexjapan.line_backend.shop.order.controller.request.OrderRequestDTO;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    private String currency;
    private Integer quantity;
    private String request;
    private String orderStatus; //enum
    private String paymentMethod;

    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer productPrice;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private Integer amount;
    private String productDescription;
    private String productBrand;
    private Integer productDiscount;
    private Date productCreatedAt;
    private Date productUpdatedAt;

    private Date createOrderTime;

    private Long userId;
    private String userEmail;
    private String userName;
    private String userPhoneNumber;

    private Integer deliveryPrice;

    private Long storeId;
    private String storeName;
    private String storeNumber;

    public OrderLog toEntity(OrderEntity order, Product product, UserEntity user, Store store) {
        return OrderLog.builder()
                .currency(order.getCurrency())
                .quantity(order.getQuantity())
                .request(order.getRequest())
                .orderStatus(order.getOrderStatus())
                .paymentMethod(order.getPaymentMethod())
                .productId(product.getProductId())
                .productName(product.getName())
                .productImageUrl(product.getImageUrl())
                .productPrice(product.getPrice())
                .status(product.getStatus())
                .amount(product.getAmount())
                .productDescription(product.getDescription())
                .productBrand(product.getBrand())
                .productDiscount(product.getDiscount())
                .productCreatedAt(product.getCreatedAt())
                .productUpdatedAt(product.getUpdatedAt())
                .createOrderTime(order.getCreateTime())
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userName(user.getName())
                .userPhoneNumber(user.getPhoneNumber())
                .deliveryPrice(order.getDeliveryPrice())
                .storeId(store.getStoreId())
                .storeName(store.getName())
                .storeNumber(store.getStoreNumber())
                .build();
    }

}
