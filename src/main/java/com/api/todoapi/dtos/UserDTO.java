package com.api.todoapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO
     (@NotBlank(groups = CreateUser.class)
     @Size(groups = CreateUser.class, min = 3, max = 50)
     String username,
     @NotBlank(groups = {CreateUser.class, UpdateUser.class})
     @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 255)
     String password) {

     public interface CreateUser {}
     public interface UpdateUser {}
}
