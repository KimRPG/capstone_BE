package com.capstonexjapan.line_backend.shop.order.controller;

import com.capstonexjapan.line_backend.shop.order.controller.request.OrderRequestDTO;
import com.capstonexjapan.line_backend.shop.order.controller.response.OrderResponseDTO;
import com.capstonexjapan.line_backend.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public String orderProduct(@RequestBody OrderRequestDTO dto) {
        orderService.orderProduct(dto);
        return "주문 완료";
    }

    @GetMapping()
    public ResponseEntity<?> getOrders(@RequestParam(required = false) Long id) {
        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(orderService.getOrderById(id));
        }

        return ResponseEntity.ok(orderService.getOrders());
    }

    @DeleteMapping("")
    public String deleteOrder(@RequestParam Long id) {
        orderService.deleteById(id);
        return "삭제됨";
    }
}
