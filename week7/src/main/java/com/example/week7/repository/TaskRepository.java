package com.example.week7.repository;

import com.example.week7.entity.Task;
import com.example.week7.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);
    Page<Task> findByUserAndStatus(User user, String status, Pageable pageable);
    Page<Task> findByUser(User user, Pageable pageable);
}
