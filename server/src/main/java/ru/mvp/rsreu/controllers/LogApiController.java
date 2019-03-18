package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.EntityLog;
import ru.mvp.database.repositories.EntityLogRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LogApiController {

    EntityLogRepository entityLogRepository;

    @Autowired
    public LogApiController(EntityLogRepository entityLogRepository) {
        this.entityLogRepository = entityLogRepository;
    }

    @RequestMapping("/api/getLogTableData")
    public String getLogTableData(@RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "searchValue") String searchValue) {
        Page<EntityLog> output;
        if (searchValue.isEmpty())
            output = entityLogRepository.findAll(PageRequest.of(pageNum, size, Direction.ASC, "time"));
        else
            output = entityLogRepository.findByFilter(PageRequest.of(pageNum, size, Direction.ASC, "time"), searchValue);
        return new Gson().toJson(fillData(output));
    }
    @RequestMapping("/api/getReportTableData")
    public String getReportTableData(@RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "searchValue") String searchValue) {
        Page<EntityLog> output;
        if (searchValue.isEmpty())
            output = entityLogRepository.findAllByName(PageRequest.of(pageNum, size, Direction.ASC, "time"), "task");
        else
            output = entityLogRepository.findByFilterByName(PageRequest.of(pageNum, size, Direction.ASC, "time"), searchValue, "task");
        return new Gson().toJson(fillData(output));
    }

    private List<HashMap<String, String>> fillData(Page<EntityLog> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("time", element.getTime().toString());
            map.put("name", element.getName());
            map.put("source", element.getSource());
            map.put("type", element.getType());
            map.put("event", element.getEvent());
            outList.add(map);});
        return outList;
    }
}
