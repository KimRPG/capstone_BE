package com.capstonexjapan.line_backend.shop.product.repository;

import com.capstonexjapan.line_backend.shop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product , Long> {
    List<ProductInfoMapping> findAllBy();
    ProductInfoMapping findByProductId(Long id);
}
