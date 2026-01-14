package com.example.adminDashboardProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.adminDashboardProject.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Custom query to get revenue for today 
    @Query("SELECT SUM(s.totalPrice) FROM Sale s WHERE DATE(s.saleDate) = CURRENT_DATE")
    Double getTotalRevenueForToday();

    // Custom query to get revenue for the current month 
    @Query("SELECT SUM(s.totalPrice) FROM Sale s WHERE YEAR(s.saleDate) = YEAR(CURRENT_DATE) AND MONTH(s.saleDate) = MONTH(CURRENT_DATE)")
    Double getTotalRevenueForCurrentMonth();

 // Added ORDER BY HOUR(s.saleDate) ASC
    @Query("SELECT HOUR(s.saleDate), SUM(s.totalPrice) FROM Sale s " +
           "WHERE DATE(s.saleDate) = CURRENT_DATE " +
           "GROUP BY HOUR(s.saleDate) " +
           "ORDER BY HOUR(s.saleDate) ASC")
    List<Object[]> getDailyChartData();

    // Added ORDER BY DATE(s.saleDate) ASC
    @Query("SELECT DATE(s.saleDate), SUM(s.totalPrice) FROM Sale s " +
           "WHERE MONTH(s.saleDate) = MONTH(CURRENT_DATE) " +
           "AND YEAR(s.saleDate) = YEAR(CURRENT_DATE) " +
           "GROUP BY DATE(s.saleDate) " +
           "ORDER BY DATE(s.saleDate) ASC")
    List<Object[]> getMonthlyChartData();
}
