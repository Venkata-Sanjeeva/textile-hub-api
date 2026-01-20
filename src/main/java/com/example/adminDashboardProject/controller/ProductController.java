package com.example.adminDashboardProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.adminDashboardProject.dto.DashboardStatsDTO;
import com.example.adminDashboardProject.dto.ProductStockDTO;
import com.example.adminDashboardProject.entity.Product;
import com.example.adminDashboardProject.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> fetchAllProducts() {
    	return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> addProduct(
            @RequestParam(value = "uniqueId", required = false) String uniqueId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile imageFile) {
        
        try {
            productService.saveProduct(uniqueId, name, description, price, brandId, categoryId, imageFile);
            return ResponseEntity.ok("Product added successfully!");
        } catch (RuntimeException e) {
            // This captures our "Unique ID already exists" and "Not found" errors
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }
    
    @GetMapping("/unique/{uniqueId}")
    public ResponseEntity<?> fetchProductByUniqueId(@PathVariable String uniqueId) {
        try {
            return ResponseEntity.ok(productService.getProductByUniqueId(uniqueId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStatistics() {
        try {
            DashboardStatsDTO stats = productService.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Log the error and return a 500 status if something fails in the DB
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductStockDTO>> getLowStockAlerts() {
        // This is the second endpoint needed for your "Actionable List"
        return ResponseEntity.ok(productService.getLowStockProducts(5)); 
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String search) {
        
    	return ResponseEntity.ok(productService.getFilteredProducts(categoryId, brandId, size, search));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<?> fetchProductById(@PathVariable String productId) {
    	return ResponseEntity.ok(productService.getProductByUniqueId(productId));
    }
    
    @GetMapping("/category/{categoryId}")
	public ResponseEntity<?> fetchAllProductsByCategory(@PathVariable Long categoryId) {
    	List<Product> listOfProducts = productService.getProductsByCategory(categoryId);

    	return ResponseEntity.ok().body(listOfProducts);
    }
    
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> fetchAllProductsByBrand(@PathVariable Long brandId) {
    	List<Product> listOfProducts = productService.getProductsByBrand(brandId);
    	
    	return ResponseEntity.ok().body(listOfProducts);
    }
    
    @PostMapping("/{id}/update-image")
    public ResponseEntity<String> updateImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            productService.updateProductImage(id, imageFile);
            return ResponseEntity.ok("Image updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to update image: " + e.getMessage());
        }
    }
    
}