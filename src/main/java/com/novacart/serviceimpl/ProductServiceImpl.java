package com.novacart.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));    
	}


	@Override
	public Product updateProduct(Long id, ProductRequest request) {

		Product product = productRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Product Not Found"));

		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setImageUrl(request.getImageUrl());
		product.setCategory(request.getCategory());

		return productRepository.save(product);
	}
	
	
	@Override
	public void deleteProduct(Long id) {

	    Product product = productRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Product Not Found"));

	    productRepository.delete(product);
	}
	
	@Override
	public List<Product> searchProducts(String keyword) {

	    return productRepository.findByNameContainingIgnoreCase(keyword);
	}
	
	@Override
	public Page<Product> getProductsWithPagination(int page, int size) {

	    return productRepository.findAll(PageRequest.of(page, size));
	}
	



}