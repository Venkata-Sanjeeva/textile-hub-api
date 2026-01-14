package com.example.adminDashboardProject.dto;

public record ProductStockDTO(
    Long id,
    String name,
    Long currentStock,
    Long variants
) {}