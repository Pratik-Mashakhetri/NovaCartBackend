package com.novacart.serviceimpl;

import com.novacart.entity.*;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.*;

import com.novacart.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        List<CartItem> cartItems =
                cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is Empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        double totalAmount = 0;

        Order order = new Order();

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();

            totalAmount +=
                    cartItem.getQuantity()
                            * cartItem.getProduct().getPrice();

            orderItems.add(orderItem);
        }

        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }
    
    
    @Override
    public List<Order> getUserOrders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        return orderRepository.findByUser(user);
    }
    
    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
    
    @Override
    public Order updateOrderStatus(
            Long orderId,
            OrderStatus status
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        order.setStatus(status);

        return orderRepository.save(order);
    }
    
    
    
    
    
    
    
    
    
}