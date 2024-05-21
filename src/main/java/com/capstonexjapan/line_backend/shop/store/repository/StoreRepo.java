package com.capstonexjapan.line_backend.shop.store.repository;

import com.capstonexjapan.line_backend.shop.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepo extends JpaRepository<Store, Long> {
}
