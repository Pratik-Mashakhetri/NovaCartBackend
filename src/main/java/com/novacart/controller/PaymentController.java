package com.novacart.controller;

import org.springframework.web.bind.annotation.*;

import com.novacart.entity.Payment;
import com.novacart.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public Payment makePayment(
            @PathVariable Long orderId
    ) {

        return paymentService.makePayment(orderId);
    }

    @GetMapping("/{orderId}")
    public Payment getPayment(
            @PathVariable Long orderId
    ) {

        return paymentService.getPaymentByOrder(orderId);
    }
}