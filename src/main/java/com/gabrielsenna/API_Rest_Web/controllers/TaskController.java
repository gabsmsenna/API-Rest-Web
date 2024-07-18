package com.gabrielsenna.API_Rest_Web.controllers;

import com.gabrielsenna.API_Rest_Web.models.Task;
import com.gabrielsenna.API_Rest_Web.services.TaskService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task taskObj = this.taskService.findById(id);
        return ResponseEntity.ok().body(taskObj);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> createTask(@Valid @RequestBody Task taskObj) {
        this.taskService.createTask(taskObj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(taskObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@Valid @RequestBody Task taskObj, @PathVariable Long id) {
        taskObj.getId();
        this.taskService.updateTask(taskObj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = this.taskService.findAllTasksByUserId(userId);
        return ResponseEntity.ok().body(tasks);
    }
}
