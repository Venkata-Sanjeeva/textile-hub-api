package com.example.adminDashboardProject.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {
    private String productUniqueId; // Links to Product.uniqueId
    private String variantUniqueId; // The new business key (SKU)
    private String color;
    private String size;
    private BigDecimal additionalPrice;
    private Integer stockQuantity;
}