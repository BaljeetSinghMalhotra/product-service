package com.microservices.practice.productservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.microservices.practice.productservice.exceptions.ProductException;
import com.microservices.practice.productservice.model.Product;
import com.microservices.practice.productservice.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository repository;

	@Override
	public Product getProductById(int id) {
		Product returnedProduct = repository.getReferenceById(id);
		logger.info("Fetched product with Id: {}", id);

		return returnedProduct;
	}

	@Override
	public List<Product> getAllProducts() throws ProductException {
		List<Product> products = repository.findAll();
		if (!products.isEmpty()) {
			return products;
		} else {
			logger.debug("Product table is empty");
			throw new ProductException("0 Products found");
		}
	}

	@Override
	public Product addProduct(Product product) throws IllegalArgumentException, DataAccessException{
		logger.debug("Saving product: {} into database",product);
		return repository.save(product);
	}

	@Override
	public Product updateProduct(Product product)throws IllegalArgumentException, DataAccessException{
		logger.debug("Updating product {} with id: {}",product.getProductName(),product.getProductId());
		return repository.save(product);
	}

	@Override
	public void removeProduct(int productId) throws ProductException {
		logger.debug("Deleting product with id: {}",productId);
		try {
			repository.delete(repository.getReferenceById(productId));
		}catch (Exception e){
			throw new ProductException("Product of given Id not found" + e.getMessage());
		}

	}

	@Override
	public Product updateProductPrice(Product product) throws ProductException {
		try {
			Product updatedProduct = repository.getReferenceById(product.getProductId());
			updatedProduct.setPrice(product.getPrice());
			repository.save(updatedProduct);
			logger.debug("Updated price of {} to :{}", updatedProduct.getProductName(),updatedProduct.getPrice());
			return updatedProduct;
		}catch (Exception e){
			throw new ProductException("Product of given Id not found" + e.getMessage());
		}

	}
}
