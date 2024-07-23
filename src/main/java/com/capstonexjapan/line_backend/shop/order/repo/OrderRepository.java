package com.capstonexjapan.line_backend.shop.order.repo;

import com.capstonexjapan.line_backend.shop.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
