package com.novacart.service;

import java.util.List;

import com.novacart.entity.Order;
import com.novacart.entity.OrderStatus;

public interface OrderService {

	Order placeOrder(
	        String email,
	        Long addressId
	);
    
    List<Order> getUserOrders(String email);
    
    List<Order> getAllOrders();
    
    Order updateOrderStatus(
            Long orderId,
            OrderStatus status
    );
    
    Order cancelOrder(Long orderId, String email);
    
    
    
}
