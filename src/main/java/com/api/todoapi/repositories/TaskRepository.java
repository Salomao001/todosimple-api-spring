package com.api.todoapi.repositories;

import com.api.todoapi.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Query> findByUser_id(long id);

//    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
//    List<Task> findByUserId(@Param("id") long id);

//    @Query(value = "Select * FROM tasks WHERE task.user_id = :id", nativeQuery = true)
//    List<Task> findByUserId(@Param("id") long id);
}
