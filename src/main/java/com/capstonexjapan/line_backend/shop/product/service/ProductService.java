package com.capstonexjapan.line_backend.shop.product.service;

import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.response.ReadProduct;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.repository.ProductRepo;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final StoreService storeService;

    public void addProduct(CreateProduct dto, String filename) {
        Store store = storeService.findById(dto.getStoreId());
        productRepo.save(new Product().toEntity(dto, store, filename));
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
        Product product =findById(id);
        return new ReadProduct().toDTO(product);
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

    @Transactional
    public void orderProduct(Product product,Integer amount) {
        product.orderProduct(amount);
    }


}
