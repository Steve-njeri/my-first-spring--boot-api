package com.todolist.todolist.repository;

import com.todolist.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository  // Marks this as a repository component
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository provides methods like:
    // - findAll() - get all tasks
    // - findById(id) - get one task
    // - save(task) - add or update a task
    // - deleteById(id) - delete a task
    // - count() - count all tasks

    // Custom method: find tasks by their completed status
    List<Task> findByCompleted(boolean completed);
}