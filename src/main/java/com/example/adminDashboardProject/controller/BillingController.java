package com.example.adminDashboardProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminDashboardProject.dto.BillingDTO;
import com.example.adminDashboardProject.service.ProductVariantService;

@RestController
@RequestMapping("/api/admin/billing")
@CrossOrigin
public class BillingController {
	
	@Autowired
	private ProductVariantService variantService;
	
	@PutMapping
	public ResponseEntity<?> updateInventory(@RequestBody List<BillingDTO> billingDto) {
	    try {
	        boolean success = variantService.updateListOfVariants(billingDto);
	        return ResponseEntity.ok(success);
	    } catch (RuntimeException e) {
	        // Returns a 400 Bad Request with the specific error message (like "Insufficient stock")
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
}
