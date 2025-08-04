package com.microservices.practice.productservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.SpyBean;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Environment environment;

    @SpyBean
    private ProductService productService;

    @Test
    public void testAddProduct() {
        Product product = new Product();
        when(productService.addProduct(product)).thenReturn(product);
        assertEquals(product, productService.addProduct(product));
    }

    @Test
    public void testGetProductById() {
        Product product = mock(Product.class);
        when(productService.getProductById(1)).thenReturn(product);
        assertEquals(product, productService.getProductById(1));
    }

    @Test
    public void testRemoveProduct() {
        doThrow(new ProductException("Product not found"))
                .when(productService)
                .removeProduct(1);
        assertThrows(ProductException.class, () -> productService.removeProduct(1));
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(products);
        assertEquals(products, productService.getAllProducts());
    }

    @Test
    public void testUpdateProductPrice() {
        Product product = mock(Product.class);
        when(productService.updateProductPrice(product)).thenReturn(product);
        assertEquals(product, productService.updateProductPrice(product));
    }

    @Test
    public void testUpdateProduct() {
        doThrow(new IllegalArgumentException("Invalid argument"))
                .when(productService)
                .updateProduct(any(Product.class));
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(new Product()));
    }
}

import { render, fireEvent, waitFor } from '@testing-library/react';
import { create } from 'react-test-renderer';
import { ProductController } from './ProductController';

describe('ProductController', () => {
    it('should add product', async () => {
        const { getByText } = render(<ProductController />);
        const addButton = getByText('Add Product');
        fireEvent.click(addButton);
        await waitFor(() => expect(getByText('Added new product successfully')).toBeInTheDocument());
    });

    it('should get product by id', async () => {
        const { getByText, getByTestId } = render(<ProductController />);
        const productIdInput = getByTestId('product-id-input');
        fireEvent.change(productIdInput, { target: { value: '1' } });
        const getProductButton = getByText('Get Product');
        fireEvent.click(getProductButton);
        await waitFor(() => expect(getByText('Fetched product with Id: 1')).toBeInTheDocument());
    });

    it('should remove product', async () => {
        const { getByTestId, getByText } = render(<ProductController />);
        const productIdInput = getByTestId('product-id-input');
        fireEvent.change(productIdInput, { target: { value: '1' } });
        const removeProductButton = getByText('Remove Product');
        fireEvent.click(removeProductButton);
        await waitFor(() => expect(getByText('Removed product successfully')).toBeInTheDocument());
    });

    it('should get all products', async () => {
        const { getByText, getByTestId } = render(<ProductController />);
        const getAllProductsButton = getByText('Get All Products');
        fireEvent.click(getAllProductsButton);
        await waitFor(() => expect(getByText('Successfully retrieved')).toBeInTheDocument());
    });

    it('should update product price', async () => {
        const { getByTestId, getByText } = render(<ProductController />);
        const productIdInput = getByTestId('product-id-input');
        fireEvent.change(productIdInput, { target: { value: '1' } });
        const updatePriceButton = getByText('Update Price');
        fireEvent.click(updatePriceButton);
        await waitFor(() => expect(getByText('Updated product with Id: 1 price to:')).toBeInTheDocument());
    });

    it('should update product', async () => {
        const { getByTestId, getByText } = render(<ProductController />);
        const productIdInput = getByTestId('product-id-input');
        fireEvent.change(productIdInput, { target: { value: '1' } });
        const updateProductButton = getByText('Update Product');
        fireEvent.click(updateProductButton);
        await waitFor(() => expect(getByText('Updated product successfully')).toBeInTheDocument());
    });
});