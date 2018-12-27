package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Task;

import java.util.List;

public interface TaskDao {

    List<Task> getAll();
    List<Task> getAll(int limit);
    List<Task> searchByValue(String value, int showSize);
    Task searchByTaskName(String name);
}
