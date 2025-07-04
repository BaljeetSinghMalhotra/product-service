package com.microservices.practice.productservice.exceptions;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(EntityNotFoundException.class)
	private ResponseEntity<String> handleUserAlreadyExistsException(EntityNotFoundException e) {
		logger.error("Product does not exist. "+e.getMessage());
		return new ResponseEntity<>("Product of the given id does not exist "+e.getMessage(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductException.class)
	private ResponseEntity<String> handleProductException(ProductException e) {
		logger.error("Products not found, "+e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<String> handleDataAccessException(DataAccessException e) {
		logger.error("Database connection issue occurred, "+e.getMessage());
		return new ResponseEntity<>(
				"An error occurred while communicating with the database. Requested operation could not be performed. "+e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.error("Invalid argument provided:, "+e.getMessage());
		return new ResponseEntity<>(
				"Invalid Input: "+e.getMessage(),
				HttpStatus.BAD_REQUEST);
	}
}
