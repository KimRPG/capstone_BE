package com.capstonexjapan.line_backend.shop.store.entity;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.store.controller.request.CreateStore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;
    private String name;
    private String brand;
    private String storeNumber;

    public Store toEntity(CreateStore dto) {
        return Store.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .storeNumber(dto.getStoreNumber())
                .build();
    }

}
