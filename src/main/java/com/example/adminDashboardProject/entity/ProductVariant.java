package com.example.adminDashboardProject.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_variants", indexes = {
    @Index(name = "idx_variant_unique_id", columnList = "variant_unique_id")
})
@Data
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // New Business Key for the Variant (e.g., SKU or Scan ID)
    @Column(name = "variant_unique_id", nullable = false, unique = true)
    private String variantUniqueId;

    private String color;
    private String size;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal additionalPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_unique_id", referencedColumnName = "unique_id", nullable = false)
    @JsonIgnore 
    private Product product;
}