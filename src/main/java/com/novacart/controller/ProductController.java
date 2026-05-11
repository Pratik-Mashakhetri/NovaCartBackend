package com.novacart.controller;

import com.novacart.dto.ProductRequest;
import com.novacart.entity.Product;
import com.novacart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody ProductRequest request) {

        return productService.addProduct(request);
    }

    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }
}