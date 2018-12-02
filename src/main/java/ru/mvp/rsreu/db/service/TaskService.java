package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.Task;
import ru.mvp.rsreu.db.repository.TaskRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskService implements IService<Task> {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
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

    @Override
    public  HashMap<String, String> fillEntityData(Task e) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("taskName", e.getTaskName());
        hashMap.put("taskType", e.getTaskType());
        hashMap.put("frequency", String.valueOf(e.getFrequency()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdated()));
        hashMap.put("nextShedule", String.valueOf(e.getNextSheduled()));
        return hashMap;
    }
}


