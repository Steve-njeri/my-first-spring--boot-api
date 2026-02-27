package com.todolist.todolist.controller;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/tasks")  // ← CHANGED FROM "/tasks" TO "/web/tasks"
public class TaskWebController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("allTasks", taskService.getAllTasks());
        model.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        try {
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("message", "Task added successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/tasks";  // ← UPDATE REDIRECT TOO
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        taskService.getTaskById(id).ifPresent(task -> model.addAttribute("task", task));
        return "edit-task";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task,
                             RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTask(id, task);
            redirectAttributes.addFlashAttribute("message", "Task updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Task not found!");
        }
        return "redirect:/web/tasks";  // ← UPDATE REDIRECT TOO
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Task not found!");
        }
        return "redirect:/web/tasks";  // ← UPDATE REDIRECT TOO
    }

    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTaskStatus(id);
        return "redirect:/web/tasks";  // ← UPDATE REDIRECT TOO
    }
}