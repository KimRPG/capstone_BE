package com.capstonexjapan.line_backend.shop.service;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.response.ReadProduct;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo productRepo;

    public void addProduct(CreateProduct dto) {
        productRepo.save(new Product().toEntity(dto));
    }

    public List<ReadProduct> readAllProduct() {
        return findAllProduct()
                .stream()
                .map(entity -> new ReadProduct().toDTO(entity))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProduct() {
        return productRepo.findAll();
    }

    public ReadProduct readProduct(Long id) {
        return new ReadProduct().toDTO(findById(id));
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    @Transactional
    public void updateById(Long id, UpdateProduct dto) {
        findById(id).update(dto);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }


}
