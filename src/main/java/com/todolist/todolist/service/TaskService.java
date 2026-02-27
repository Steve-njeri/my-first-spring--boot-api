package com.todolist.todolist.service;

import com.todolist.todolist.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    List<Task> getCurrentUserTasks();
    Optional<Task> getTaskById(Long id);
    Optional<Task> getTaskByIdAndCurrentUser(Long id);
    Task saveTask(Task task);
    void deleteTask(Long id);
    void deleteTaskForCurrentUser(Long id);
    Task updateTask(Long id, Task taskDetails);
    Task updateTaskForCurrentUser(Long id, Task taskDetails);
    Task toggleTaskStatus(Long id);
    Task toggleTaskStatusForCurrentUser(Long id);
}