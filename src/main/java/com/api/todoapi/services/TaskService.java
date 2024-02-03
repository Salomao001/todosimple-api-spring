package com.api.todoapi.services;

import com.api.todoapi.dtos.TaskDTO;
import com.api.todoapi.models.Task;
import com.api.todoapi.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Lazy
    private UserService userService;

    public Task findById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public List<Task> findAllByUserId(long id) {
        return taskRepository.findAllByUser_id(id);
    }

    @Transactional
    public Task create(TaskDTO taskDTO) {
        var user = userService.findById(taskDTO.user_id());
        var task = new Task(user, taskDTO.description());
        return taskRepository.save(task);
    }

    @Transactional
    public String update(TaskDTO taskDTO, long id) {
        var task = findById(id);
        task.setDescription(taskDTO.description());
        taskRepository.save(task);
        return String.format("Descrição atualizada com sucesso: \n%s", task.getDescription());
    }

    public String delete(long id) {
        var task = findById(id);
        taskRepository.delete(task);
        return String.format("Task de id: %d deletada com sucesso", task.getId());
    }

    public void deleteAll(List<Task> tasks) {
        taskRepository.deleteAll(tasks);
    }
}
