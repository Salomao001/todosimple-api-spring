package com.api.todoapi.services;

import com.api.todoapi.dtos.UserDTO;
import com.api.todoapi.exceptions.ObjectNotFoundException;
import com.api.todoapi.models.User;
import com.api.todoapi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private TaskService taskService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public User create(UserDTO userDTO) {
        var user = new User(userDTO.username(), userDTO.password());
        return userRepository.save(user);
    }

    @Transactional
    public String update(UserDTO userDTO, long id) {
        var user = findById(id);
        user.updateFromDTO(userDTO);
        userRepository.save(user);
        return "Usuário atualizado com sucesso";
    }

    public String delete(long id) {
        var user = findById(id);
        if (!user.getTasks().isEmpty()) {
            taskService.deleteAll(user.getTasks());
        }
        userRepository.delete(user);
        return String.format("usuário: %s deletado com sucesso", user.toString());
    }
}
