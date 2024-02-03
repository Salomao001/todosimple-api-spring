package com.api.todoapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor
public class Task {

    public Task(User user, String description) {
        this.user = user;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Setter
    @NotBlank
    @Column(nullable = false)
    private String description;

}
