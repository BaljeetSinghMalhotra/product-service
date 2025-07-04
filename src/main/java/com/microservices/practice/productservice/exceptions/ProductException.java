package com.microservices.practice.productservice.exceptions;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductException extends RuntimeException {

	public ProductException(String message) {
		super(message);
	}

}
