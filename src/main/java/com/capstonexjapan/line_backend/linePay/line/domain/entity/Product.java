package com.capstonexjapan.line_backend.linePay.line.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Product {
    @Id
    private String productId;
    private String name;
    private String imageUrl;
    private int quantity;
    private int price;

}