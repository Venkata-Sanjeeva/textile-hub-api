package com.example.adminDashboardProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminDashboardProject.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api/products/filter")
public class FilterController {
	
	@Autowired
	private ProductService productService;

//	@GetMapping
//	public ResponseEntity<?> filterProducts(
//			@RequestParam Long categoryId,
//			@RequestParam Long brandId,
//			@RequestParam String size) {
//		
//		return ResponseEntity.ok(productService.getFilteredProducts(categoryId, brandId, size));
//	}
}
