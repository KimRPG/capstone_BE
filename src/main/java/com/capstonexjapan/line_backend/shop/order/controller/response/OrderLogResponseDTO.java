package com.capstonexjapan.line_backend.shop.order.controller.response;

import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderLogResponseDTO {
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
}
