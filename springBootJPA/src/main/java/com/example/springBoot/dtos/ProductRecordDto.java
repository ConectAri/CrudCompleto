package com.example.springBoot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDto(@NotBlank String nome,@NotNull BigDecimal valor, @NotNull int quantidade, String descricao) {

}