package com.novacart.service;

import java.util.List;

import com.novacart.entity.Order;

public interface OrderService {

    Order placeOrder(String email);
    
    List<Order> getUserOrders(String email);
    
    List<Order> getAllOrders();
    
    
    
}
