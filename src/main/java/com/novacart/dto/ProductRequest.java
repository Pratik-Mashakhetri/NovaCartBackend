package com.novacart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String imageUrl;	
    private String category;
}