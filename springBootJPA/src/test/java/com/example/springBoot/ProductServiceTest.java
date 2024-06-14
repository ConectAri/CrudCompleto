package com.example.springBoot;

import com.example.springBoot.models.ProductModel;
import com.example.springBoot.repositories.ProductRepository;
import com.example.springBoot.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarProduto() {
        ProductModel productModel = new ProductModel();
        productModel.setName("Test");
        productModel.setValue(BigDecimal.valueOf(100));
        productModel.setQuantidade(10);
        productModel.setDescricao("Test Description");

        when(productRepository.save(any(ProductModel.class))).thenReturn(productModel);

        ProductModel savedProduct = productService.criarProduto(productModel);

        assertEquals("Test", savedProduct.getName());
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    @Test
    public void testBuscarPorId() {
        UUID productId = UUID.randomUUID();
        ProductModel productModel = new ProductModel();
        productModel.setName("Test");
        productModel.setValue(BigDecimal.valueOf(100));
        productModel.setQuantidade(10);
        productModel.setDescricao("Test Description");

        when(productRepository.findById(productId)).thenReturn(Optional.of(productModel));

        ProductModel foundProduct = productService.buscarPorId(productId);

        assertEquals("Test", foundProduct.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testAtualizarProduto() {
        UUID productId = UUID.randomUUID();
        ProductModel existingProduct = new ProductModel();
        existingProduct.setName("Test");
        existingProduct.setValue(BigDecimal.valueOf(100));
        existingProduct.setQuantidade(10);
        existingProduct.setDescricao("Test Description");

        ProductModel newProductDetails = new ProductModel();
        newProductDetails.setName("Updated Test");
        newProductDetails.setValue(BigDecimal.valueOf(200));
        newProductDetails.setQuantidade(20);
        newProductDetails.setDescricao("Updated Test Description");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(ProductModel.class))).thenReturn(newProductDetails);

        ProductModel updatedProduct = productService.atualizarProduto(productId, newProductDetails);

        assertEquals("Updated Test", updatedProduct.getName());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    @Test
    public void testDeletarProduto() {
        UUID productId = UUID.randomUUID();

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deletarProduto(productId);

        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }


}