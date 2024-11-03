package com.capstonexjapan.line_backend.shop.product.repository;

import com.capstonexjapan.line_backend.shop.product.entity.ProductStatus;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

public interface ProductInfoMapping {
    Long getProductId();
    String getName();
    String getImageUrl();
    Integer getPrice();

    ProductStatus getStatus();

    Integer getAmount();
    String getDescription();
    String getBrand();
    Integer getDiscount();
    Date getCreatedAt();
    Date getUpdatedAt();

    Store getStore();
}
