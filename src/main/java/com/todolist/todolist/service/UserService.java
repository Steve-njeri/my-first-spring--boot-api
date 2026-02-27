package com.todolist.todolist.service;

import com.todolist.todolist.model.User;
import com.todolist.todolist.dto.UserRegistrationDto;
import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User getCurrentUser();
}