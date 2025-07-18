package com.example.week6.repository;

import com.example.week6.entity.Task;
import com.example.week6.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);
}
