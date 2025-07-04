package com.microservices.practice.productservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.practice.productservice.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
}
