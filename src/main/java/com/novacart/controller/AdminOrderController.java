package com.novacart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novacart.entity.Order;
import com.novacart.entity.OrderStatus;
import com.novacart.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }
    
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {

        return orderService.updateOrderStatus(
                orderId,
                status
        );
    }
}