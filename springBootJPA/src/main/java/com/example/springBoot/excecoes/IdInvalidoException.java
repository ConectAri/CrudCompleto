package com.example.springBoot.excecoes;

public class IdInvalidoException extends RuntimeException{
    public IdInvalidoException(String mensagem) {
        super(mensagem);
    }
}
