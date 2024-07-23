package com.capstonexjapan.line_backend.shop.order.service;

import com.capstonexjapan.line_backend.shop.order.controller.request.OrderRequestDTO;
import com.capstonexjapan.line_backend.shop.order.entity.OrderEntity;
import com.capstonexjapan.line_backend.shop.order.repo.OrderRepository;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.repository.ProductRepo;
import com.capstonexjapan.line_backend.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;


    @Transactional
    public void orderProduct(OrderRequestDTO dto) {
        Product product = productService.findById(dto.getProductId());
        productService.orderProduct(product, dto.getQuantity());
        orderRepository.save(new OrderEntity().toEntity(dto));
    }
}
