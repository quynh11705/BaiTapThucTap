package com.example.week7.controller;

import com.example.week7.entity.Task;
import com.example.week7.entity.User;
import com.example.week7.repository.TaskRepository;
import com.example.week7.repository.UserRepository;
import com.example.week7.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final JwtService jwtService;

    public TaskController(TaskRepository taskRepo, UserRepository userRepo, JwtService jwtService) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    private User getCurrentUser(HttpServletRequest request) {
        String email = jwtService.extractUsernameFromRequest(request);
        return userRepo.findByEmail(email).orElseThrow();
    }

    @GetMapping
    public ResponseEntity<?> getTasks(
            HttpServletRequest request,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        User user = getCurrentUser(request);

        if (page == null || size == null) {
            List<Task> tasks = taskRepo.findByUser(user);
            return ResponseEntity.ok(tasks);
        }

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> result;
        if (status != null) {
            result = taskRepo.findByUserAndStatus(user, status, pageable);
        } else {
            result = taskRepo.findByUser(user, pageable);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id, HttpServletRequest request) {
        User user = getCurrentUser(request);
        return taskRepo.findById(id)
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build());
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task, HttpServletRequest request) {
        User user = getCurrentUser(request);
        task.setUser(user);
        return ResponseEntity.ok(taskRepo.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task newTask, HttpServletRequest request) {
        User user = getCurrentUser(request);
        return taskRepo.findById(id).map(task -> {
            if (!task.getUser().getId().equals(user.getId())) {
                return new ResponseEntity<>((Task) null, HttpStatus.FORBIDDEN);
            }
            task.setTitle(newTask.getTitle());
            task.setDescription(newTask.getDescription());
            task.setStatus(newTask.getStatus());

            return ResponseEntity.ok(taskRepo.save(task));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        User user = getCurrentUser(request);
        return taskRepo.findById(id)
                .<ResponseEntity<Void>>map(task -> {
                    if (!task.getUser().getId().equals(user.getId())) {
                        return ResponseEntity.status(403).build();
                    }
                    taskRepo.delete(task);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
