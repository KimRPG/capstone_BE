package com.capstonexjapan.line_backend.shop.product.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProduct {
    private String name;
    private String imageUrl;
    private Integer price;
    private Integer amount;
    private String description;
}
