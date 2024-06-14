package com.example.springBoot;

import com.example.springBoot.controllers.ProductController;
import com.example.springBoot.dtos.ProductRecordDto;
import com.example.springBoot.models.ProductModel;
import com.example.springBoot.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        ProductRecordDto productRecordDto = new ProductRecordDto("Test", BigDecimal.valueOf(100), 10, "Test Description");
        ProductModel productModel = new ProductModel();
        productModel.setName(productRecordDto.nome());
        productModel.setValue(productRecordDto.valor());
        productModel.setQuantidade(productRecordDto.quantidade());
        productModel.setDescricao(productRecordDto.descricao());

        when(productService.criarProduto(any(ProductModel.class))).thenReturn(productModel);

        ResponseEntity<ProductModel> response = productController.saveProduct(productRecordDto);

        assertEquals(201, response.getStatusCodeValue());
        verify(productService, times(1)).criarProduto(any(ProductModel.class));
    }
}





