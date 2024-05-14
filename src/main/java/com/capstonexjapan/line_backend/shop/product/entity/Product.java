package com.capstonexjapan.line_backend.shop.product.entity;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;

    @Column(name = "image_url")
    private String imageUrl;
    private Integer price;

    private boolean isSoldOut;

    private Integer amount;
    private String description;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;


//    @ManyToOne
//    private Store store;

    public Product toEntity(CreateProduct dto) {
        return Product.builder()
                .name(dto.getName())
                .imageUrl(dto.getImageUrl())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .build();
    }

    public void update(UpdateProduct dto) {
        this.name = dto.getName();
        this.imageUrl = dto.getImageUrl();
        this.price = dto.getPrice();
        this.isSoldOut = dto.isSoldOut();
        this.amount = dto.getAmount();
        this.description = dto.getDescription();
    }
}
