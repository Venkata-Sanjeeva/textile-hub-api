package com.example.adminDashboardProject.entity;

import jakarta.persistence.*;
import lombok.Data;

// Moved outside or kept here, but usually best to use @Enumerated inside the entity

@Entity
@Data
// Use @Table to avoid conflicts with the database's internal "User" table
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    // It's good practice to ensure usernames are unique
    @Column(unique = true, nullable = false)
    private String username;
    
    // This tells JPA to store the word "ADMIN" instead of the number 0
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @Column(nullable = false)
    private String password;
}