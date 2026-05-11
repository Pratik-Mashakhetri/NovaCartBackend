package com.novacart.service;

import com.novacart.dto.ProductRequest;
import com.novacart.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductRequest request);

    List<Product> getAllProducts();
    
    Product getProductById(Long id);
}