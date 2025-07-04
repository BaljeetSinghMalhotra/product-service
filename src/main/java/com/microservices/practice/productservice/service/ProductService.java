package com.microservices.practice.productservice.service;

import java.util.List;

import com.microservices.practice.productservice.exceptions.ProductException;
import com.microservices.practice.productservice.model.Product;
import org.springframework.dao.DataAccessException;

public interface ProductService {

	Product getProductById(int id);

	List<Product> getAllProducts() throws ProductException;

	Product addProduct(Product product) throws IllegalArgumentException, DataAccessException;

	Product updateProduct(Product product) throws IllegalArgumentException, DataAccessException;

	void removeProduct(int productId) throws ProductException;

	Product updateProductPrice(Product product) throws ProductException;

}