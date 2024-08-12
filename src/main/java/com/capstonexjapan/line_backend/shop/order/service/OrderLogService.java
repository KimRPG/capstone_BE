package com.capstonexjapan.line_backend.shop.order.service;

import com.capstonexjapan.line_backend.shop.order.controller.request.OrderRequestDTO;
import com.capstonexjapan.line_backend.shop.order.entity.OrderData;
import com.capstonexjapan.line_backend.shop.order.repo.OrderLogRepository;
import com.capstonexjapan.line_backend.shop.product.entity.Product;
import com.capstonexjapan.line_backend.shop.product.repository.ProductRepo;
import com.capstonexjapan.line_backend.shop.store.entity.Store;
import com.capstonexjapan.line_backend.shop.store.repository.StoreRepo;
import com.capstonexjapan.line_backend.shop.user.entity.UserEntity;
import com.capstonexjapan.line_backend.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderLogService {
    private final OrderLogRepository orderLogRepository;
    private final UserRepository userRepository;
    private final ProductRepo productRepo;
    private final StoreRepo storeRepo;

    @Transactional
    public void orderLog(OrderRequestDTO dto) {
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow();
        Product product = productRepo.findById(dto.getProductId()).orElseThrow();
        Store store = storeRepo.findById(dto.getStoreId()).orElseThrow();
        OrderData orderLog = new OrderData().toEntity(dto,product,user,store, dto.getAddressDTO());

        orderLogRepository.save(orderLog);
    }

}
