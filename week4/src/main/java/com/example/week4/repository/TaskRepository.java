package com.example.week4.repository;

import com.example.week4.entity.Task;
import com.example.week4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);
}
