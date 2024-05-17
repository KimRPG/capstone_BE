package com.capstonexjapan.line_backend.shop.product.controller.request;

import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProduct  {
    private String name;
    private String imageUrl;
    private Integer price;
    private ProductStatus status;
    private Integer amount;
    private String description;
}
