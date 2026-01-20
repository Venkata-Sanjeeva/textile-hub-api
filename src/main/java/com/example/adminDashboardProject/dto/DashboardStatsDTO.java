package com.example.adminDashboardProject.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private BigDecimal totalValue;
    private Long totalStock;
    private Long outOfStockCount;
    private Long totalBrands;
    private Long totalCategories;
    private Map<String, Long> categoryDistribution;
    private Map<String, Long> brandDistribution;
}
