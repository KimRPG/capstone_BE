package com.capstonexjapan.line_backend.shop.order.service;

import com.capstonexjapan.line_backend.shop.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


}
