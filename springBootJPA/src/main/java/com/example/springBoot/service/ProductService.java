package com.example.springBoot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springBoot.excecoes.IdInvalidoException;
import com.example.springBoot.excecoes.ProdutoInvalidoException;
import com.example.springBoot.excecoes.ProdutoNaoEncontradoException;
import com.example.springBoot.models.ProductModel;
import com.example.springBoot.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public ProductModel criarProduto(ProductModel product) {
        
        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        System.out.println(product.toString());
        if (product.getValue() == null || product.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do produto inválido");
        }

        if (product.getIdProduct() != null && productRepository.existsById(product.getIdProduct())) {
            throw new IllegalArgumentException("ID de produto já existe");
        }

        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }

        return productRepository.save(product);
    }
    //-----------------------------------------------------------------------------------------------------------

    public List<ProductModel> listarProduto() {
        return productRepository.findAll();
    }

  //-----------------------------------------------------------------------------------------------------------
    public ProductModel buscarPorId(UUID productId) {
        
        if (productId == null) {
            throw new IdInvalidoException("ID de produto inválido");
        }
     
        Optional<ProductModel> optionalProduct = productRepository.findById(productId);
        
        // Verifica se o produto foi encontrado
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
    }
  //-----------------------------------------------------------------------------------------------------------

    public ProductModel atualizarProduto(UUID productId, ProductModel newProductDetails) {

        ProductModel existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(newProductDetails.getName());
            existingProduct.setValue(newProductDetails.getValue());
            existingProduct.setQuantidade(newProductDetails.getQuantidade());
            existingProduct.setDescricao(newProductDetails.getDescricao());

            return productRepository.save(existingProduct);
        }
        return null;
    }
//-----------------------------------------------------------------------------------------------------------
    public void deletarProduto(UUID productId) {
        if (productId == null) {
            throw new ProdutoInvalidoException("ID de produto inválido");
        }

        if (!productRepository.existsById(productId)) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        productRepository.deleteById(productId);
    }




//------------------------------------------------------------------
   
   
}