package com.example.adminDashboardProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminDashboardProject.entity.Brand;
import com.example.adminDashboardProject.repository.BrandRepository;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    // Get all brands
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    // Add a new brand (e.g., Nike, Zara, etc.)
    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand savedBrand = brandRepository.save(brand);
        return new ResponseEntity<>(savedBrand, HttpStatus.CREATED);
    }

    // Update a brand name
    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brandDetails) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        
        brand.setName(brandDetails.getName());
        return ResponseEntity.ok(brandRepository.save(brand));
    }
}
