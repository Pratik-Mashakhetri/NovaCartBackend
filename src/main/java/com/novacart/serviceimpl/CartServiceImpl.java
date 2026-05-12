package com.novacart.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novacart.entity.CartItem;
import com.novacart.entity.Product;
import com.novacart.entity.User;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.CartItemRepository;
import com.novacart.repository.ProductRepository;
import com.novacart.repository.UserRepository;
import com.novacart.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartItem addToCart(Long productId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Not Found"));

        CartItem cartItem = cartItemRepository
                .findByUserAndProductId(user, productId)
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(cartItem.getQuantity() + 1);

            cartItem.setTotalPrice(
                    cartItem.getQuantity() * product.getPrice()
            );

        } else {

            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(1)
                    .totalPrice(product.getPrice())
                    .build();
        }

        return cartItemRepository.save(cartItem);
    }
    
    @Override
    public List<CartItem> getUserCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        return cartItemRepository.findByUser(user);
    }
    
    @Override
    public CartItem updateCartQuantity(
            Long cartItemId,
            int quantity,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Item Not Found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized Access");
        }

        cartItem.setQuantity(quantity);

        cartItem.setTotalPrice(
                quantity * cartItem.getProduct().getPrice()
        );

        return cartItemRepository.save(cartItem);
    }
    
    @Override
    public void removeCartItem(
            Long cartItemId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Item Not Found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized Access");
        }

        cartItemRepository.delete(cartItem);
    }
    
    
    
}