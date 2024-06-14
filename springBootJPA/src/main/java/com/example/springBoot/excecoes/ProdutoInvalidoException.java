package com.example.springBoot.excecoes;

public class ProdutoInvalidoException extends  RuntimeException{
    public ProdutoInvalidoException(String mensagem) {
        super(mensagem);
}
}