package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(String category);
	
	//@Query(value = "SELECT * FROM product p WHERE p.name = ?1", nativeQuery = true)
	//@Query(value = "SELECT * FROM product p WHERE p.name = :keyword", nativeQuery = true)
	//@Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :keyword, '%')")
	@Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :keyword, '%') OR p.brand LIKE CONCAT('%', :keyword, '%') OR p.category LIKE CONCAT('%', :keyword, '%') OR p.description LIKE CONCAT('%', :keyword, '%')")
	List<Product> searchProducts(String keyword);
}
