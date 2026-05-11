package com.novacart.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacart.dto.ProductRequest;
import com.novacart.entity.Product;
import com.novacart.exception.ResourceNotFoundException;
import com.novacart.repository.ProductRepository;
import com.novacart.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(ProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }
    
    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));    }
}