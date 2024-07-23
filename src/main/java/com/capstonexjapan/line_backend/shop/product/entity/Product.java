package com.capstonexjapan.line_backend.shop.product.entity;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
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
    private ProductStatus status;

    private Integer amount;
    private String description;

    private String brand;

    private Integer discount;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;



    public Product toEntity(CreateProduct dto,Store store, String imageUrl) {
        return Product.builder()
                .name(dto.getName())
                .imageUrl(imageUrl)
                .price(dto.getPrice())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .discount(dto.getDiscount())
                .store(store)
                .build();
    }

    public void update(UpdateProduct dto) {
        if (StringUtils.isNotBlank(dto.getName())) this.name = dto.getName();
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
