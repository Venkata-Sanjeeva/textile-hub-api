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
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile imageFile) {
        
        try {
            productService.saveProduct(name, description, price, brandId, categoryId, imageFile);
            return ResponseEntity.ok("Product added successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
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
    public ResponseEntity<?> fetchProductById(@PathVariable Long productId) {
    	return ResponseEntity.ok(productService.getProductById(productId));
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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product and image deleted successfully");
    }
    
}