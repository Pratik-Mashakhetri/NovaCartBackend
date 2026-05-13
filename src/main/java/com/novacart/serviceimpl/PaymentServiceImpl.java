package com.novacart.serviceimpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.novacart.entity.Order;
import com.novacart.entity.OrderStatus;
import com.novacart.entity.Payment;
import com.novacart.entity.PaymentStatus;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.OrderRepository;
import com.novacart.repository.PaymentRepository;
import com.novacart.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public Payment makePayment(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order Not Found"));

        Payment payment = Payment.builder()
                .transactionId(UUID.randomUUID().toString())
                .amount(order.getTotalAmount())
                .paymentStatus(PaymentStatus.SUCCESS)
                .paymentDate(LocalDateTime.now())
                .order(order)
                .build();

        order.setStatus(OrderStatus.PAID);
        
        
        //Reduce Stock 
        order.getOrderItems().forEach(item -> {

            item.getProduct().setStockQuantity(
                    item.getProduct().getStockQuantity()
                            - item.getQuantity()
            );
        });

        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByOrder(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment Not Found"));
    }
}