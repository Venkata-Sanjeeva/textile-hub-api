package com.example.adminDashboardProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adminDashboardProject.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
    // Spring automatically generates the SQL to find all categories
    List<Category> findAll();
    
    boolean existsByName(String name);
}
