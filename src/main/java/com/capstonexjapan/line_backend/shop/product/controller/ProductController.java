package com.capstonexjapan.line_backend.shop.product.controller;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public String createProduct(@RequestBody CreateProduct dto) {
        productService.addProduct(dto);
        return "추가됨";
    }

    @GetMapping("")
    public ResponseEntity<?> readProducts(@RequestParam(required = false) Long id) {
        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(productService.readProduct(id));
        }
        return ResponseEntity.ok(productService.readAllProduct());


    }

    @DeleteMapping("")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteById(id);
        return "삭제됨";
    }

    @PatchMapping("")
    public String updateProduct(@RequestParam Long id, @RequestBody UpdateProduct dto) {
        productService.updateById(id, dto);
        return "업데이트 됨";
    }


}
