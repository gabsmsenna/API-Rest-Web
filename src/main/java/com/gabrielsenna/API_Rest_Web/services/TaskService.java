package com.gabrielsenna.API_Rest_Web.services;

import com.gabrielsenna.API_Rest_Web.models.Task;
import com.gabrielsenna.API_Rest_Web.models.User;
import com.gabrielsenna.API_Rest_Web.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Task not found | " + id));
    }

    @Transactional
    public Task createTask(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task updateTask(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void deleteTask(Long id) {
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir esta task! Há entidades relacionadas");
        }
    }

}


