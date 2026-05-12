package com.novacart.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novacart.entity.CartItem;
import com.novacart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{productId}")
    public CartItem addToCart(
            @PathVariable Long productId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return cartService.addToCart(productId, email);
    }
    
    @GetMapping
    public List<CartItem> getUserCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return cartService.getUserCart(email);
    }
    
    @PutMapping("/{cartItemId}")
    public CartItem updateCartQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return cartService.updateCartQuantity(
                cartItemId,
                quantity,
                email
        );
    }
    
    @DeleteMapping("/{cartItemId}")
    public String removeCartItem(
            @PathVariable Long cartItemId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        cartService.removeCartItem(cartItemId, email);

        return "Cart Item Removed Successfully";
    }
    
    
    
    
    
    
    
}