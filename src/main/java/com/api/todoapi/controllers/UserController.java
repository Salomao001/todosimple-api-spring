package com.api.todoapi.controllers;

import com.api.todoapi.dtos.UserDTO;
import com.api.todoapi.models.User;
import com.api.todoapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        var tasks = userService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @Validated(UserDTO.CreateUser.class)
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDTO userDTO) {
        var user = userService.create(userDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserDTO userDTO) {
        var token = userService.login(userDTO);
        return ResponseEntity.ok(token);
    }

    @Validated(UserDTO.UpdateUser.class)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid UserDTO userDTO, @PathVariable long id) {
        var res = userService.update(userDTO, id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        var res = userService.delete(id);
        return ResponseEntity.ok(res);
    }
}
