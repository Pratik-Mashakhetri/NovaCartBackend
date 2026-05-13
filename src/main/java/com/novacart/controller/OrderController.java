package com.novacart.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novacart.entity.Order;
import com.novacart.entity.OrderStatus;
import com.novacart.entity.OrderStatus;
import com.novacart.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(
            @RequestParam Long addressId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.placeOrder(
                email,
                addressId
        );
    }
    
    
    @GetMapping("/my-orders")
    public List<Order> getUserOrders(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.getUserOrders(email);
    }
    
    
    @PutMapping("/cancel/{orderId}")
    public Order cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return orderService.cancelOrder(orderId, email);
    }
    
    @PutMapping("/admin/status/{orderId}")
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