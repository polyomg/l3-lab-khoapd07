package com.poly.lab7.dao;

import com.poly.lab7.entity.Product;
import com.poly.lab7.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(double minPrice, double maxPrice);


    Page<Product> findAllByNameLike(String keywords, Pageable pageable);

    @Query("SELECT o.category AS group, SUM(o.price) AS sum, COUNT(o) AS count "
            + "FROM Product o "
            + "GROUP BY o.category "
            + "ORDER BY SUM(o.price) DESC")
    List<Report> getInventoryByCategory();
}
