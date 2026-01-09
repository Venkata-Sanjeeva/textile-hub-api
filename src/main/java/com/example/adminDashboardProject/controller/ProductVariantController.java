package com.example.adminDashboardProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminDashboardProject.dto.ProductVariantDTO;
import com.example.adminDashboardProject.entity.ProductVariant;
import com.example.adminDashboardProject.service.ProductVariantService;

@RestController
@RequestMapping("/api/admin/variants")
@CrossOrigin
public class ProductVariantController {

    @Autowired
    private ProductVariantService variantService;
    
    @GetMapping("/{productId}")
    public ResponseEntity<?> fetchAllVariantsByProductId(@PathVariable Long productId) {
    	return ResponseEntity.ok(variantService.getAllVariantsByProductId(productId));
    }

    @PutMapping("/update-stock")
    public ResponseEntity<?> updateStock(@RequestBody ProductVariant productVariant) {
        if(variantService.updateVariant(productVariant) == null) {
        	ResponseEntity.badRequest();
        }
        return ResponseEntity.ok("Inventory updated successfully");
    }
    
    @PostMapping("/bulk-create")
    public ResponseEntity<String> createVariants(@RequestBody List<ProductVariantDTO> variantDTOs) {
        variantService.addVariantsToProduct(variantDTOs);
        return ResponseEntity.ok("Variants added successfully");
    }
    
    @DeleteMapping("/{variantId}")
    public ResponseEntity<Boolean> deleteVariantById(@PathVariable Long variantId) {
    	boolean isDeleted = variantService.deleteVariant(variantId);
    	
    	return ResponseEntity.ok(isDeleted);
    }
}