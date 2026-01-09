package com.example.adminDashboardProject.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductVariantDTO {
	
	private String color;
    private String size;
    private BigDecimal additionalPrice;
    private Integer stockQuantity;
    private Long productId; // Just the ID, not the whole object
}
