package com.capstonexjapan.line_backend.shop.product.controller.response;

import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadProduct {
    private Long productId;
    private String name;
    private String image;
    private Integer price;
    private ProductStatus status;
    private Integer amount;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public ReadProduct toDTO(Product entity) {
        return ReadProduct.builder()
                .productId(entity.getProductId())
                .name(entity.getName())
                .image(entity.getImageUrl())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
