package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.rsreu.db.dao.TaskDao;
import ru.mvp.rsreu.db.entity.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class TaskApiController {

    private TaskDao taskDao;

    @Autowired
    public TaskApiController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @RequestMapping("/api/getTaskTableData")
    public String getTaskTableData(@RequestParam(value = "size", required = false, defaultValue = "10") String size) {//todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Task> list = taskDao.getAll(showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillTaskData(e);
            tableData.add(hashMap);
        });
        return new Gson().toJson(tableData);
    }

    @RequestMapping("/api/searchTaskData")
    public String searchTaskData(@RequestParam(value = "size", required = false, defaultValue = "10") String size,    //todo для показа конечно и так сойдет, но уж дюже похоже на предыдущий метод, фабрика ESLDao
                                 @RequestParam(value = "searchValue") String searchValue) {                           //todo получать мапу? не станет ли избыточным?
        int showSize = Integer.valueOf(size);
        List<HashMap<String, String>> tableData = new ArrayList<>(showSize);
        List<Task> list = taskDao.searchByValue(searchValue, showSize);
        list.forEach(e -> {
            HashMap<String, String> hashMap = fillTaskData(e);
            tableData.add(hashMap);
        });
        return new Gson().toJson(tableData);
    }

    private HashMap<String, String> fillTaskData(Task e) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("taskName", e.getTaskName());
        hashMap.put("taskType", e.getTaskType());
        hashMap.put("frequency", String.valueOf(e.getFrequency()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdated()));
        hashMap.put("nextShedule", String.valueOf(e.getNextSheduled()));
        return hashMap;
    }
}
