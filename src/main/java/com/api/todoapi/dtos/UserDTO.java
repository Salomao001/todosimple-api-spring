package com.api.todoapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO
     (@NotBlank
     @Size(min = 3, max = 50)
     String username,
     @NotBlank
     @Size(min = 8, max = 255)
     String password) {
}
