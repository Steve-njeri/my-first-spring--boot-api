package com.todolist.todolist.service;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getCurrentUserTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByIdAndCurrentUser(Long id) {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByIdAndUser(id, currentUser);
    }

    @Override
    public Task saveTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        // Ensure the task has a user
        if (task.getUser() == null) {
            task.setUser(userService.getCurrentUser());
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteTaskForCurrentUser(Long id) {
        User currentUser = userService.getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));
        taskRepository.delete(task);
    }

    @Override
    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (taskDetails.getTitle() != null && !taskDetails.getTitle().trim().isEmpty()) {
            existingTask.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            existingTask.setDescription(taskDetails.getDescription());
        }
        existingTask.setCompleted(taskDetails.isCompleted());

        return taskRepository.save(existingTask);
    }

    @Override
    public Task updateTaskForCurrentUser(Long id, Task taskDetails) {
        User currentUser = userService.getCurrentUser();
        Task existingTask = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));

        if (taskDetails.getTitle() != null && !taskDetails.getTitle().trim().isEmpty()) {
            existingTask.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            existingTask.setDescription(taskDetails.getDescription());
        }
        existingTask.setCompleted(taskDetails.isCompleted());

        return taskRepository.save(existingTask);
    }

    @Override
    public Task toggleTaskStatus(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    @Override
    public Task toggleTaskStatusForCurrentUser(Long id) {
        User currentUser = userService.getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }
}