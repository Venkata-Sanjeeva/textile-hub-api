package com.example.adminDashboardProject.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long variantId;
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime saleDate; // This is crucial for "Daily/Monthly" filters
}