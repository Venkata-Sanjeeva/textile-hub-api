package com.example.adminDashboardProject.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.adminDashboardProject.dto.DashboardStatsDTO;
import com.example.adminDashboardProject.dto.ProductStockDTO;
import com.example.adminDashboardProject.entity.Brand;
import com.example.adminDashboardProject.entity.Category;
import com.example.adminDashboardProject.entity.Product;
import com.example.adminDashboardProject.repository.BrandRepository;
import com.example.adminDashboardProject.repository.CategoryRepository;
import com.example.adminDashboardProject.repository.ProductRepository;

@Service
public class ProductService {

	// CHANGE THIS: Move it outside the project
	// Windows: "C:/my-app-uploads/"  |  Mac/Linux: "/Users/yourname/uploads/"
	private final String UPLOAD_DIR = "C:/my-app-uploads/";
	
    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private BrandRepository brandRepo;
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    public List<Product> getAllProducts() {
    	return productRepo.findAll();
    }
    
    public void saveProduct(String name, String desc, Double price, Long bId, Long cId, MultipartFile file) throws IOException {
        // 1. Handle File Storage
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);	// This line will copy the image through inputStream into given location through filePath
        }

        // 2. Save Product Details to Database
        Product product = new Product();
        product.setName(name);
        product.setDescription(desc);
        product.setBasePrice(new BigDecimal(price));
        
        Category catObj = new Category();
        catObj.setId(cId);
        
        product.setCategory(catObj);
        
        Brand brandObj = new Brand();
        brandObj.setId(bId);
        
        product.setBrand(brandObj);
        
        product.setImageUrl(fileName);
        
        // Note: You would fetch Brand and Category objects using their IDs here
        
        productRepo.save(product);
        
    }
    
    public Product getProductById(Long id) {
    	return productRepo.findById(id).orElse(null);
    }
    
    public void updateProductImage(Long productId, MultipartFile newFile) throws IOException {
        // 1. Find the existing product
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 2. Delete the old physical file if it exists
        String oldFileName = product.getImageUrl();
        if (oldFileName != null) {
            Path oldFilePath = Paths.get(UPLOAD_DIR).resolve(oldFileName);
            try {
                Files.deleteIfExists(oldFilePath);
                System.out.println("Old image deleted: " + oldFileName);
            } catch (IOException e) {
                System.err.println("Could not delete old file: " + e.getMessage());
            }
        }

        // 3. Save the new physical file
        String newFileName = System.currentTimeMillis() + "_" + newFile.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        
        try (InputStream inputStream = newFile.getInputStream()) {
            Path newFilePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // 4. Update the database with the new filename
        product.setImageUrl(newFileName);
        productRepo.save(product);
    }
    
    public void deleteProduct(Long id) {
        // 1. Check if product exists safely
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        try {
            // 2. Locate and Delete the file
            if (product.getImageUrl() != null) {
                Path filePath = Paths.get(UPLOAD_DIR + product.getImageUrl());
                Files.deleteIfExists(filePath);
                System.out.println("File deleted: " + filePath);
            }
        } catch (Exception e) {
            // Log the error but continue to delete the record from DB 
            // so the user isn't stuck with a ghost record
            System.err.println("Could not delete file, but proceeding with DB deletion: " + e.getMessage());
        }

        // 3. Delete from Database
        productRepo.deleteById(id);
    }
    
    public DashboardStatsDTO getDashboardStats() {
        BigDecimal totalValue = productRepo.getTotalInventoryValue();
        Long totalStock = productRepo.getTotalStockCount();
        Long outOfStock = productRepo.countOutOfStockProducts();
        
        // Convert List<Object[]> to a clean Map for the frontend
        Map<String, Long> distribution = productRepo.getProductCountByCategory().stream()
            .collect(Collectors.toMap(
                array -> (String) array[0],
                array -> (Long) array[1]
            ));

        return new DashboardStatsDTO(
            totalValue != null ? totalValue : BigDecimal.ZERO,
            totalStock != null ? totalStock : 0L,
            outOfStock,
            brandRepo.count(), // Assumes you have BrandRepository
            categoryRepo.count(),
            distribution
        );
    }
    
    public List<ProductStockDTO> getLowStockProducts(int threshold) {
        List<Object[]> results = productRepo.findLowStockProducts((long) threshold);
        
        return results.stream()
            .map(result -> new ProductStockDTO((Long) result[0], (String) result[1], (Long) result[2], (Long) result[3]))
            .collect(Collectors.toList());
    }
    
    public List<Product> getFilteredProducts(Long categoryId, Long brandId, String size, String search) {
    	return productRepo.findFilteredProducts(categoryId, brandId, size, search);
    }
    
    public List<Product> getProductsByCategory(Long categoryId) {
    	List<Product> listOfProducts = productRepo.findByCategoryId(categoryId);
    	
    	return listOfProducts;
    }
    
    public List<Product> getProductsByBrand(Long brandId) {
    	List<Product> listOfProducts = productRepo.findByBrandId(brandId);
    	
    	return listOfProducts;
    }
    
}