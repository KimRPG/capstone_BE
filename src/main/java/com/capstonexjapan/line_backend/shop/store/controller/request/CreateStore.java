package com.capstonexjapan.line_backend.shop.store.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStore {

    private String name;
    private String storeNumber;
}
