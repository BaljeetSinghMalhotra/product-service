package com.microservices.practice.productservice.controller;

import com.microservices.practice.productservice.exceptions.ProductException;
import com.microservices.practice.productservice.model.Product;
import com.microservices.practice.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-service")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private Environment environment;
	
	@Autowired
	private ProductService productService;
	
	@Operation(summary = "Get a Product by Id", description = "Returns a product based on the given productId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "404", description = "Not found - The product was not found")
	})
	@GetMapping("/product/{productId}")
	public Product viewProductById(@PathVariable("productId") int productId){
		 Product product = productService.getProductById(productId);
		 product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("Changed Code");
		 
		 return product;
	}

	@Operation(summary = "Get all Products", description = "Returns all products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "404", description = "Not found - There are no Products to fetch")
	})
	@GetMapping("/products")
	public ResponseEntity<List<Product>> viewAllProduct() {
		
		return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
	}

	@Operation(summary = "Create Product", description = "Create a new product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Product created successfully"),
			@ApiResponse(responseCode = "400", description = "Illegal Arguments passed - Product cannot be created"),
			@ApiResponse(responseCode = "404", description = "Database Connection issue - Product was not created")
	})
	@PostMapping("/product")
	public ResponseEntity<String> addProduct(@RequestBody Product product) throws ProductException {
		logger.info("adding new product: {}",product);
		productService.addProduct(product);
		
		return new ResponseEntity<String>("Added new product successfully", HttpStatus.CREATED);
	}

	@Operation(summary = "Update an existing Product", description = "Update product of given Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product updated successfully"),
			@ApiResponse(responseCode = "400", description = "Illegal Arguments passed - Product cannot be updated"),
			@ApiResponse(responseCode = "404", description = "Database Connection issue - Product was not updated")
	})
	@PutMapping("/product")
	public ResponseEntity<String> updateProduct(@RequestBody Product product) throws ProductException {
		logger.info("updating product with id: {}",product.getProductId());
		productService.updateProduct(product);
		
		return new ResponseEntity<String>("Updated product successfully", HttpStatus.OK);
	}

	@Operation(summary = "Remove a Product by Id", description = "Deletes a product of given productId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successfully deleted, No content returned"),
			@ApiResponse(responseCode = "404", description = "Not found - The product was not found")
	})
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> removeProductById(@PathVariable("productId") int productId)
			throws ProductException {
		productService.removeProduct(productId);
		
		return new ResponseEntity<String>("Removed product successfully", HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Update price of an existing Product", description = "Update price of a product by specifying the productId")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product updated successfully"),
			@ApiResponse(responseCode = "400", description = "Illegal Arguments passed - Product cannot be updated"),
			@ApiResponse(responseCode = "404", description = "Database Connection issue - Product was not updated")
	})
	@PatchMapping("/product/price")
	public ResponseEntity<String> updateProductPrice(@RequestBody Product product)
			throws ProductException {
		Product updatedProduct = productService.updateProductPrice(product);
		
		return new ResponseEntity<String>("Updated "+updatedProduct.getProductName()+" price to: "+updatedProduct.getPrice(), HttpStatus.OK);
	}
}
