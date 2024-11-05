package com.capstonexjapan.line_backend.shop.product.entity;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.repository.ProductInfoMapping;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.AVAILABLE;

    private Integer amount;
    private String description;

    private String brand;

    private Integer discount;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @Column(columnDefinition = "vector(1536)")
    private float[] embedding;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Product(Long productId, String name, String imageUrl, Integer price, ProductStatus status, Integer amount, String description, String brand, Integer discount, Date createdAt, Date updatedAt, Store store) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.brand = brand;
        this.discount = discount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.store = store;
    }

    public Product toEntity(ProductInfoMapping dto) {
        return Product.builder()
                .productId(dto.getProductId())
                .name(dto.getName())
                .imageUrl(dto.getImageUrl())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .discount(dto.getDiscount())
                .status(ProductStatus.AVAILABLE)
                .store(dto.getStore())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
    public Product toEntity(CreateProduct dto,Store store, String imageUrl, float[] embedding) {
        return Product.builder()
                .name(dto.getName())
                .imageUrl(imageUrl)
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .discount(dto.getDiscount())
                .status(ProductStatus.AVAILABLE)
                .store(store)
                .embedding(embedding)
                .build();
    }
    public Product toEntity(CreateProduct dto,Store store, float[] embedding) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .discount(dto.getDiscount())
                .status(ProductStatus.AVAILABLE)
                .store(store)
                .embedding(embedding)
                .build();
    }
    public void update(UpdateProduct dto, float[] embedding) {
        if (StringUtils.isNotBlank(dto.getName())) {
            this.name = dto.getName();
            this.embedding = embedding;
        };
        if (StringUtils.isNotBlank(dto.getImageUrl())) this.imageUrl = dto.getImageUrl();
        if (Objects.nonNull(dto.getPrice())) this.price = dto.getPrice();
        if (Objects.nonNull(dto.getStatus())) this.status = dto.getStatus();
        if (Objects.nonNull(dto.getAmount())) this.amount = dto.getAmount();
        if (Objects.nonNull(dto.getDiscount())) this.discount = dto.getDiscount();
        if (StringUtils.isNotBlank(dto.getDescription())) this.description = dto.getDescription();
        if (StringUtils.isNotBlank(dto.getBrand())) this.brand = dto.getBrand();
    }

    public void orderProduct(Integer amount) {
        this.amount -= amount;
    }
}
