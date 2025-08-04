package com.microservices.practice.productservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        when(repository.save(any(Product.class))).thenReturn(new Product());
        when(repository.getReferenceById(anyInt())).thenReturn(new Product());
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        productService.addProduct(product);
        verify(repository).save(product);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        when(repository.getReferenceById(1)).thenReturn(product);
        Product retrievedProduct = productService.getProductById(1);
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testRemoveProduct() {
        productService.removeProduct(1);
        verify(repository).delete(anyInt());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = List.of(new Product());
        when(repository.findAll()).thenReturn(products);
        List<Product> retrievedProducts = productService.getAllProducts();
        assertEquals(products, retrievedProducts);
    }

    @Test
    public void testUpdateProductPrice() {
        Product product = new Product();
        product.setPrice(10.0);
        Product updatedProduct = productService.updateProductPrice(product);
        verify(repository).save(any(Product.class));
        assertEquals(product.getPrice(), updatedProduct.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        productService.updateProduct(product);
        verify(repository).save(any(Product.class));
    }
}

import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import ProductServiceImpl from './ProductServiceImpl';

describe('ProductServiceImpl', () => {
    it('should add product', async () => {
        const product = new Product();
        const productService = new ProductServiceImpl();
        const { getByText } = render(<ProductServiceImpl />);
        fireEvent.change(product, 'name', 'Test Product');
        productService.addProduct(product);
        expect(getByText('Test Product')).toBeInTheDocument();
    });

    it('should get product by id', async () => {
        const productId = 1;
        const productService = new ProductServiceImpl();
        when(productService.repository.getReferenceById(productId)).thenReturn(new Product());
        const { getByText } = render(<ProductServiceImpl />);
        const retrievedProduct = await productService.getProductById(productId);
        expect(getByText('Test Product')).toBeInTheDocument();
    });

    it('should remove product', async () => {
        const productId = 1;
        const productService = new ProductServiceImpl();
        when(productService.repository.getReferenceById(productId)).thenReturn(new Product());
        productService.removeProduct(productId);
        expect(productService.productRepository.delete).toHaveBeenCalledTimes(1);
    });

    it('should get all products', async () => {
        const productService = new ProductServiceImpl();
        when(productService.repository.findAll()).thenReturn([new Product()]);
        const { getByText } = render(<ProductServiceImpl />);
        const retrievedProducts = await productService.getAllProducts();
        expect(getByText('Test Product')).toBeInTheDocument();
    });

    it('should update product price', async () => {
        const productId = 1;
        const product = new Product();
        product.setPrice(10.0);
        const productService = new ProductServiceImpl();
        when(productService.repository.getReferenceById(productId)).thenReturn(new Product());
        const updatedProduct = await productService.updateProductPrice(product);
        expect(updatedProduct.getPrice()).toBe(10.0);
    });

    it('should update product', async () => {
        const productId = 1;
        const product = new Product();
        const productService = new ProductServiceImpl();
        when(productService.repository.getReferenceById(productId)).thenReturn(new Product());
        const updatedProduct = await productService.updateProduct(product);
        expect(updatedProduct.getPrice()).toBe(10.0);
    });
});