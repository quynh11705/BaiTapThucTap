package com.example.week5.controller;

import com.example.week5.entity.Task;
import com.example.week5.entity.User;
import com.example.week5.repository.TaskRepository;
import com.example.week5.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    public AdminController(UserRepository userRepo, TaskRepository taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}

