package com.capstonexjapan.line_backend.shop.store.controller.response;

import com.capstonexjapan.line_backend.shop.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadStore {
    private Long storeId;
    private String name;
    private String brand;
    private String storeNumber;

    public ReadStore toDTO(Store entity) {
        return ReadStore.builder()
                .storeId(entity.getStoreId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .storeNumber(entity.getStoreNumber())
                .build();
    }
}
