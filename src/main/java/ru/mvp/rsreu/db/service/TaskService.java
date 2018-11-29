package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.Task;
import ru.mvp.rsreu.db.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("task")
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> searchByValue(String value, Pageable pageable) {
        List<Task> fullList = taskRepository.findAll();
        List<Task> resultList = new ArrayList<>(pageable.getPageSize());
        Iterator<Task> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < pageable.getPageSize()) {
            Task tempTask = iterator.next();
            if (tempTask.getTaskType().contains(value.toLowerCase()) || tempTask.getTaskName().toLowerCase().contains(value.toLowerCase())) {
                resultList.add(tempTask);
                i++;
            }
        }
        return resultList;
    }
}


