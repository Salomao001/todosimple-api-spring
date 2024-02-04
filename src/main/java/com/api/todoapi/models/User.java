package com.api.todoapi.models;

import com.api.todoapi.UserRole;
import com.api.todoapi.dtos.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public void updateFromDTO(UserDTO userDTO) {
        this.username = userDTO.username();
        this.password = userDTO.password();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 50, updatable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_roles")
    @Column(name = "role", nullable = false)
    private Set<Integer> roles = new HashSet<>();

    @Override
    public String toString() {
        return username;
    }

    public void cryptographyPassword(String cryptPass) {
        this.password = cryptPass;
    }

    public Set<UserRole> getRoles() {
        return this.roles.stream().map(UserRole::toEnum).collect(Collectors.toSet());
    }


    public void addProfile(UserRole role) {
        this.roles.add(role.getCode());
    }
}
