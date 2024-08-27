package com.capstonexjapan.line_backend.shop.product.controller;

import com.capstonexjapan.line_backend.ftp.FtpServer;
import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.service.ProductService;
import com.capstonexjapan.line_backend.shop.product.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final S3Service s3Service;
    private final FtpServer ftpServer;

    @PostMapping("")
    public String createProduct(@RequestPart CreateProduct dto,
                                @RequestPart(required = false) MultipartFile file)throws IOException {
        String fileName = s3Service.uploadFile(file);
        productService.addProduct(dto, fileName);
        return "추가됨";
    }

    @PostMapping("/practice")
    public void hi(@RequestPart(required = false)MultipartFile file) throws IOException {
        ftpServer.upload(file);
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
