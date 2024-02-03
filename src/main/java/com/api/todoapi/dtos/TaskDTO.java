package com.api.todoapi.dtos;

import com.api.todoapi.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDTO
        (@NotNull
        long user_id,
        @NotBlank
        String descripton) {
}
