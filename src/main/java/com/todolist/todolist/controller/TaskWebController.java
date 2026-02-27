package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.service.TaskService;
import com.todolist.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/tasks")
public class TaskWebController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("allTasks", taskService.getCurrentUserTasks());
        model.addAttribute("newTask", new Task());
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        try {
            task.setUser(userService.getCurrentUser());
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("message", "Task added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return taskService.getTaskByIdAndCurrentUser(id)
                .map(task -> {
                    model.addAttribute("task", task);
                    return "edit-task";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Task not found or access denied");
                    return "redirect:/web/tasks";
                });
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task,
                             RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTaskForCurrentUser(id, task);
            redirectAttributes.addFlashAttribute("message", "Task updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Task not found or access denied!");
        }
        return "redirect:/web/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTaskForCurrentUser(id);
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Task not found or access denied!");
        }
        return "redirect:/web/tasks";
    }

    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.toggleTaskStatusForCurrentUser(id);
            redirectAttributes.addFlashAttribute("message", "Task status updated!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Task not found or access denied!");
        }
        return "redirect:/web/tasks";
    }
}