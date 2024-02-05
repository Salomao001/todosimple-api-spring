package com.api.todoapi.services;

import com.api.todoapi.UserRole;
import com.api.todoapi.dtos.UserDTO;
import com.api.todoapi.exceptions.ObjectNotFoundException;
import com.api.todoapi.models.User;
import com.api.todoapi.repositories.UserRepository;
import com.api.todoapi.security.JWTUtil;
import com.api.todoapi.security.UserSecurityDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public User create(UserDTO userDTO) {
        var user = new User(userDTO.username(), userDTO.password());
        user.cryptographyPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.addProfile(UserRole.USER);

        return userRepository.save(user);
    }

    public String login(UserDTO userDTO) {
        var user = new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password());
        var auth = authenticationManager.authenticate(user);

        var token = jwtUtil.generateToken((UserSecurityDetails) auth.getPrincipal());

        return token;
    }

    @Transactional
    public String update(UserDTO userDTO, long id) {
        var user = findById(id);
        user.updateFromDTO(userDTO);
        user.cryptographyPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
