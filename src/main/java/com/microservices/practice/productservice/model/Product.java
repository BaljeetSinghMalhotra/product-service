package com.microservices.practice.productservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {

	@Id
	private int productId;
	private String productName;
	private Double price;
	private String manufacturer;
	private int port;

	public Product(int id, String productName, Double price, String manufacturer) {
		this.productId = id;
		this.productName = productName;
		this.price = price;
		this.manufacturer = manufacturer;
	}
}
