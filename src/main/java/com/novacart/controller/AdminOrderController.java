package com.novacart.controller;

import com.novacart.entity.Order;
import com.novacart.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {

        return orderService.getAllOrders();
    }
}