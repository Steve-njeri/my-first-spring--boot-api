package com.todolist.todolist.model;

import jakarta.persistence.*;

@Entity  // This tells Spring that this class will be stored in a database
public class Task {

    @Id  // This marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Database will auto-generate IDs
    private Long id;  // Unique identifier for each task

    @Column(nullable = false)  // This field cannot be empty in database
    private String title;  // Task title (e.g., "Buy groceries")

    private String description;  // Task description (e.g., "Buy milk, eggs, bread")

    private boolean completed;  // Whether the task is done (true/false)

    // Constructor with no parameters (required by JPA)
    public Task() {
    }

    // Constructor with parameters (convenient for creating tasks)
    public Task(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // Getters and Setters - these allow other classes to access private fields


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}