package com.novacart.service;

import com.novacart.entity.Payment;

public interface PaymentService {

    Payment makePayment(Long orderId);

    Payment getPaymentByOrder(Long orderId);

}