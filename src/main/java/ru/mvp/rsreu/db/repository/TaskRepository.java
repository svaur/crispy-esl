package ru.mvp.rsreu.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mvp.rsreu.db.entity.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
}