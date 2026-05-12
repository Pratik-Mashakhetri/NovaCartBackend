package com.novacart.service;

import java.util.List;

import com.novacart.entity.CartItem;

public interface CartService {

    CartItem addToCart(Long productId, String email);
    
    List<CartItem> getUserCart(String email);
    
    CartItem updateCartQuantity(
            Long cartItemId,
            int quantity,
            String email
    );
    
    void removeCartItem(
            Long cartItemId,
            String email
    );
}