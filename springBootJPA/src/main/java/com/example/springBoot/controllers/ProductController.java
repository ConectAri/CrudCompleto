package com.example.springBoot.controllers;

import com.example.springBoot.dtos.ProductRecordDto;
import com.example.springBoot.excecoes.ProdutoNaoEncontradoException;
import com.example.springBoot.models.ProductModel;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/produto")
public class ProductController {

    @Autowired
    private com.example.springBoot.service.ProductService productService;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        productModel.setName(productRecordDto.nome());
        productModel.setValue(productRecordDto.valor());
        productModel.setQuantidade(productRecordDto.quantidade());
        productModel.setDescricao(productRecordDto.descricao());
        ProductModel savedProduct = productService.criarProduto(productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productList = productService.listarProduto();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable(value = "id") UUID id) {
        try {
            ProductModel productModel = productService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(productModel);
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable(value = "id") UUID id,
         @RequestBody @Valid ProductModel productRecordDto) {
        try {
            ProductModel atualizarProduto = productService.atualizarProduto(id, productRecordDto);
            return ResponseEntity.status(HttpStatus.OK).body(atualizarProduto);
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        try {
            productService.deletarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
        } catch (ProdutoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }


}
