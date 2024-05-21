package com.capstonexjapan.line_backend.shop.store.controller;

import com.capstonexjapan.line_backend.shop.store.controller.request.CreateStore;
import com.capstonexjapan.line_backend.shop.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    @PostMapping("")
    public String createStore(@RequestBody CreateStore dto) {
        storeService.createStore(dto);
        return "추가됨";
    }

    @GetMapping("")
    public ResponseEntity<?> readProducts(@RequestParam(required = false) Long id) {
//        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(storeService.readStore(id));
//        }
//        return ResponseEntity.ok(storeService.readStore());
    }
}
