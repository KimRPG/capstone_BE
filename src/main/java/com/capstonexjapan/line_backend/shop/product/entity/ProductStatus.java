package com.capstonexjapan.line_backend.shop.product.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    SOLD_OUT,
    DISCOUNTED,
    AVAILABLE,
    UNAVAILABLE
}
