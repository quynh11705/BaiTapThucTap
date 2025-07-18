package com.example.week6.service;

import com.example.week6.entity.Task;
import com.example.week6.entity.User;
import com.example.week6.repository.TaskRepository;
import com.example.week6.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task getTaskById(Long id, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Access denied");
        }
        return task;
    }

    public Task createTask(Task task, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Access denied");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Access denied");
        }
        taskRepository.delete(task);
    }
}
