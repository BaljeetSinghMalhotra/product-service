package com.microservices.practice.productservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.SpyBean;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductService service;

    @Mock
    private Environment environment;

    @Mock
    private ProductRepository repository;

    @Test
    void testAddProduct() {
        // Arrange
        Product product = new Product();
        product.setProductName("product-name");
        product.setPrice(10.0);

        when(repository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = service.addProduct(product);

        // Assert
        assertThat(result).isEqualTo(product);
    }

    @Test
    void testGetProductById() {
        // Arrange
        int id = 1;
        Product product = new Product();
        product.setProductName("product-name");
        product.setPrice(10.0);

        when(repository.getReferenceById(any(Integer.class))).thenReturn(product);

        // Act
        Product result = service.getProductById(id);

        // Assert
        assertThat(result).isEqualTo(product);
    }

    @Test
    void testRemoveProduct() {
        // Arrange
        int id = 1;

        doNothing().when(repository()).delete(any(Product.class));

        // Act
        service.removeProduct(id);

        // Assert
        verify(repository(), times(1)).delete(any(Product.class));
    }

    @Test
    void testUpdateProductPrice() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("product-name");
        product.setPrice(10.0);

        when(repository.getReferenceById(any(Integer.class))).thenReturn(product);
        when(repository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = service.updateProductPrice(product);

        // Assert
        assertThat(result).isEqualTo(product);
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("product-name");
        product.setPrice(10.0);

        when(repository.getReferenceById(any(Integer.class))).thenReturn(product);
        when(repository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = service.updateProduct(product);

        // Assert
        assertThat(result).isEqualTo(product);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(repository.findAll()).thenReturn(products);

        // Act
        List<Product> result = service.getAllProducts();

        // Assert
        assertThat(result).isEqualTo(products);
    }
}

import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import ProductController from './ProductController';

describe('ProductController', () => {
  it('should update product price', async () => {
    const productService = jest.fn((product) => {
      return { updatePrice: jest.fn() };
    });

    const environment = { getProperty: jest.fn(() => 'localhost') };

    const controller = new ProductController(productService, environment);

    const { getByText } = render(<ProductController />);
    const inputField = getByText('Update price of an existing product');
    fireEvent.change(inputField, { target: { value: 10.0 } });

    await waitFor(() => expect(controller.updateProductPrice({ productId: 1 })).toBe(true));
  });

  it('should update product name', async () => {
    const productService = jest.fn((product) => {
      return { updateName: jest.fn() };
    });

    const environment = { getProperty: jest.fn(() => 'localhost') };

    const controller = new ProductController(productService, environment);

    const { getByText } = render(<ProductController />);
    const inputField = getByText('Update name of an existing product');
    fireEvent.change(inputField, { target: { value: 'new-name' } });

    await waitFor(() => expect(controller.updateProduct({ productId: 1 })).toBe(true));
  });
});