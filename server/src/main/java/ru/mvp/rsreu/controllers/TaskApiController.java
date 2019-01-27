package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.Tasks;
import ru.mvp.database.repositories.TasksRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class TaskApiController {

    TasksRepository tasksRepository;

    @Autowired
    public TaskApiController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping("/api/getTaskTableData")
    public String getTaskTableData(@RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "searchValue") String searchValue) {
        Page<Tasks> output;
        if (searchValue.isEmpty())
            output = tasksRepository.findAll(PageRequest.of(pageNum, size, Direction.ASC, "taskName"));
        else
            output = tasksRepository.findByFilter(PageRequest.of(pageNum, size, Direction.ASC, "taskName"), searchValue);
        return new Gson().toJson(fillTaskData(output));
    }

    private List<HashMap<String, String>> fillTaskData(Page<Tasks> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("taskName", element.getTaskName());
            map.put("frequency", element.getCronExpression());
            map.put("taskResults", element.getTaskResultsById().toString());
            map.put("updatedItemParams", element.getTaskUpdatedItemParamsById().toString());
            map.put("status", String.valueOf(element.getStatus()));
            outList.add(map);});
        return outList;
    }
}
