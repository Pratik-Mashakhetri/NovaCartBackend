package com.novacart.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novacart.dto.ProductRequest;
import com.novacart.entity.Product;
import com.novacart.service.ProductService;

import lombok.RequiredArgsConstructor;

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
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {

        return productService.getProductById(id);
    }
    
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {

        return productService.updateProduct(id, request);
    }
    
    
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product Deleted Successfully";
    }
    
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword
    ) {

        return productService.searchProducts(keyword);
    }
    
    @GetMapping("/pagination")
    public Page<Product> getProductsWithPagination(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return productService.getProductsWithPagination(page, size);
    }
    
    
}