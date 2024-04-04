package com.example.Form.Test.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonRecordDto(
        @NotBlank @NotNull String name,
        @NotBlank @NotNull String email,
        @NotNull int age,
        @NotBlank @NotNull String cpf) {
}