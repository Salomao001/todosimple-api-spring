package com.api.todoapi.dtos;

import com.api.todoapi.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDTO
        (@NotNull(groups = CreateTask.class)
        long user_id,
        @NotBlank(groups = {CreateTask.class, UpdateTask.class})
        String description) {

        public interface CreateTask {}
        public interface UpdateTask {}
}
