package com.todolist.todolist.service;

import com.todolist.todolist.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task saveTask(Task task);
    void deleteTask(Long id);
    Task updateTask(Long id, Task taskDetails);
    Task toggleTaskStatus(Long id);
}