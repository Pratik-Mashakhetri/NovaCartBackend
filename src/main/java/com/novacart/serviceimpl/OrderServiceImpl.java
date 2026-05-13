package com.novacart.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacart.entity.Address;
import com.novacart.entity.CartItem;
import com.novacart.entity.Order;
import com.novacart.entity.OrderItem;
import com.novacart.entity.OrderStatus;
import com.novacart.entity.User;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.AddressRepository;
import com.novacart.repository.CartItemRepository;
import com.novacart.repository.OrderRepository;
import com.novacart.repository.ProductRepository;
import com.novacart.repository.UserRepository;
import com.novacart.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(
            String email,
            Long addressId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address Not Found"));

        if (!address.getUser().getId().equals(user.getId())) {

            throw new RuntimeException("Unauthorized Address Access");
        }

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

        // Shipping Address Snapshot
        order.setFullName(address.getFullName());
        order.setMobileNumber(address.getMobileNumber());
        order.setAddressLine(address.getAddressLine());
        order.setCity(address.getCity());
        order.setState(address.getState());
        order.setPincode(address.getPincode());
        order.setCountry(address.getCountry());

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
    
    @Override
    public Order cancelOrder(Long orderId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        // security check
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // prevent multiple cancel
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order Already Cancelled");
        }

        // restore stock
        order.getOrderItems().forEach(item -> {

            item.getProduct().setStockQuantity(
                    item.getProduct().getStockQuantity()
                            + item.getQuantity()
            );

            productRepository.save(item.getProduct());
        });

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }
}