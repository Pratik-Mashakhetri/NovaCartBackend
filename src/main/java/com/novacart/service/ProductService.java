package com.novacart.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.novacart.dto.ProductRequest;
import com.novacart.entity.Product;

public interface ProductService {

    Product addProduct(ProductRequest request);

    List<Product> getAllProducts();
    
    Product getProductById(Long id);
    
    Product updateProduct(Long id, ProductRequest request);
    
    void deleteProduct(Long id);
    
    List<Product> searchProducts(String keyword);
    
    Page<Product> getProductsWithPagination(int page, int size);
}