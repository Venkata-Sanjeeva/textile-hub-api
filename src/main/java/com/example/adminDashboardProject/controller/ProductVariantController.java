package com.example.adminDashboardProject.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.adminDashboardProject.dto.BillingDTO;
import com.example.adminDashboardProject.dto.ProductVariantDTO;
import com.example.adminDashboardProject.entity.ProductVariant;
import com.example.adminDashboardProject.service.ProductVariantService;

@RestController
@RequestMapping("/api/admin/variants")
@CrossOrigin
public class ProductVariantController {

    @Autowired
    private ProductVariantService variantService;

    // 1. Fetch variants by the Product's Unique ID
    @GetMapping("/product/{productUniqueId}")
    public ResponseEntity<List<ProductVariant>> fetchAllVariantsByProductUniqueId(@PathVariable String productUniqueId) {
        return ResponseEntity.ok(variantService.getAllVariantsByProductUniqueId(productUniqueId));
    }

    // 2. Update stock for a specific variant (Admin Manual Update)
    @PutMapping("/update-stock")
    public ResponseEntity<String> updateStock(@RequestBody ProductVariant productVariant) {
        ProductVariant updated = variantService.updateVariant(productVariant);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Variant not found");
        }
        return ResponseEntity.ok("Inventory updated successfully");
    }

    // 3. NEW: Billing/Checkout Endpoint (Bulk Stock Reduction)
//    @PostMapping("/process-billing")
//    public ResponseEntity<String> processBilling(@RequestBody List<BillingDTO> billingDto) {
//        try {
//            boolean success = variantService.processBilling(billingDto);
//            if (success) {
//                return ResponseEntity.ok("Billing processed and stock updated");
//            } else {
//                return ResponseEntity.badRequest().body("Billing failed: One or more variants not found or insufficient stock");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    // 4. Bulk Create Variants for a Product
    @PostMapping("/bulk-create")
    public ResponseEntity<String> createVariants(@RequestBody List<ProductVariantDTO> variantDTOs) {
        try {
            variantService.addVariantsToProduct(variantDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body("Variants added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create variants: " + e.getMessage());
        }
    }

    // 5. Delete Variant
    @DeleteMapping("/{variantId}")
    public ResponseEntity<Boolean> deleteVariantById(@PathVariable Long variantId) {
        boolean isDeleted = variantService.deleteVariant(variantId);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.ok(true);
    }
}