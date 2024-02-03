package com.api.todoapi.controllers;

import com.api.todoapi.dtos.TaskDTO;
import com.api.todoapi.models.Task;
import com.api.todoapi.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable long id) {
        var task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getAllByUserId(@PathVariable long userId) {
        var tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @Validated(TaskDTO.CreateTask.class)
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid TaskDTO taskDTO) {
        var task = taskService.create(taskDTO);
        return ResponseEntity.ok(task);
    }

    @Validated(TaskDTO.UpdateTask.class)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid TaskDTO taskDTO, @PathVariable long id) {
        var res = taskService.update(taskDTO, id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        var res = taskService.delete(id);
        return ResponseEntity.ok(res);
    }

}
