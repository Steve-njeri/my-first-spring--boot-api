package com.todolist.todolist.repository;

import com.todolist.todolist.model.Task;
import com.todolist.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByUserAndCompleted(User user, boolean completed);

    Optional<Task> findByIdAndUser(Long id, User user);

    void deleteByIdAndUser(Long id, User user);

    boolean existsByIdAndUser(Long id, User user);
}