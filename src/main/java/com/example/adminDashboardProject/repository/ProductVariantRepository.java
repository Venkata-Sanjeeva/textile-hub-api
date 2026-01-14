package com.example.adminDashboardProject.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.adminDashboardProject.entity.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    
    // Find variants belonging to a specific product
    List<ProductVariant> findByProduct_UniqueId(String productUniqueId);
    
    // Find a specific variant by its own unique ID (for QR scanning)
    Optional<ProductVariant> findByVariantUniqueId(String variantUniqueId);
    
    boolean existsByVariantUniqueId(String variantUniqueId);
}