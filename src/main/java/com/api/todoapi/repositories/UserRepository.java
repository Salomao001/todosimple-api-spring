package com.api.todoapi.repositories;

import com.api.todoapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
