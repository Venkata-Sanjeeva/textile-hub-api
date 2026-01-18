package com.example.adminDashboardProject.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_unique_id", columnList = "unique_id")
})
@Data
@ToString(exclude = {"variants", "brand", "category"}) // Exclude associations to prevent recursion
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Added NotBlank for validation and kept unique=true
    @NotBlank(message = "Unique ID is required")
    @Column(name = "unique_id", nullable = false, unique = true, length = 50)
    private String uniqueId;
    
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2) // Added precision for financial data
    private BigDecimal basePrice;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();

    // Helper method to sync bidirectional relationship
    public void addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProduct(this);
    }
    
    public void removeVariant(ProductVariant variant) {
        variants.remove(variant);
        variant.setProduct(null);
    }
}