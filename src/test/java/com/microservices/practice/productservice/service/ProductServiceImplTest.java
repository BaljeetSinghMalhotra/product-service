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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @SpyBean
    private ProductService productService;

    @Test
    public void testGetProductById() {
        // Given
        Product product = new Product(1, "Product Name", 10.0);
        when(productRepository.getReferenceById(any())).thenReturn(product);

        // When
        Product result = productService.getProductById(1);

        // Then
        assertEquals(product, result);
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Given
        when(productRepository.getReferenceById(any())).thenThrow(Exception.class);

        // When
        Product product = new Product(1, "Product Name", 10.0);
        Product result = productService.getProductById(1);

        // Then
        assertThrows(ProductException.class, () -> result);
    }

    @Test
    public void testAddProduct() {
        // Given
        Product product = new Product(1, "Product Name", 10.0);
        when(productRepository.save(any())).thenReturn(product);

        // When
        Product result = productService.addProduct(product);

        // Then
        assertEquals(product, result);
    }

    @Test
    public void testAddProductException() {
        // Given
        doThrow(new Exception()).when(productRepository).save(any());

        // When
        assertThrows(DataAccessException.class, () -> productService.addProduct(new Product(1, "Product Name", 10.0)));
    }

    @Test
    public void testUpdateProductPrice() {
        // Given
        Product product = new Product(1, "Product Name", 10.0);
        when(productRepository.getReferenceById(any())).thenReturn(product);

        // When
        Product result = productService.updateProductPrice(product);

        // Then
        assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    public void testUpdateProductPriceNotFound() {
        // Given
        doThrow(new Exception()).when(productRepository).getProductById(any());

        // When
        assertThrows(ProductException.class, () -> productService.updateProductPrice(new Product(1, "Product Name", 10.0)));
    }

    @Test
    public void testUpdateProduct() {
        // Given
        Product product = new Product(1, "Product Name", 10.0);
        when(productRepository.getReferenceById(any())).thenReturn(product);

        // When
        Product result = productService.updateProduct(product);

        // Then
        assertEquals(product, result);
    }

    @Test
    public void testUpdateProductException() {
        // Given
        doThrow(new Exception()).when(productRepository).updateProduct(any());

        // When
        assertThrows(DataAccessException.class, () -> productService.updateProduct(new Product(1, "Product Name", 10.0)));
    }

    @Test
    public void testRemoveProduct() {
        // Given
        when(productRepository.deleteReferenceById(any())).thenReturn(true);

        // When
        productService.removeProduct(1);

        // Then
        verify(productRepository).deleteReferenceById(1);
    }

    @Test
    public void testRemoveProductNotFound() {
        // Given
        doThrow(new Exception()).when(productRepository).deleteReferenceById(any());

        // When
        assertThrows(ProductException.class, () -> productService.removeProduct(1));
    }

    @Test
    public void testGetAllProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertEquals(products, result);
    }

    @Test
    public void testGetAllProductsException() {
        // Given
        doThrow(new Exception()).when(productRepository).findAll();

        // When
        assertThrows(ProductException.class, () -> productService.getAllProducts());
    }
}