package com.capstonexjapan.line_backend.shop.order.repo;

import com.capstonexjapan.line_backend.shop.order.entity.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderData, Long> {

}
