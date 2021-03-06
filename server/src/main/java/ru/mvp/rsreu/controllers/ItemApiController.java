package ru.mvp.rsreu.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Items;
import ru.mvp.database.repositories.ItemsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@RestController
public class ItemApiController {

    private static final String EMPTY_STRING = "";
    private ItemsRepository itemsRepository;

    @Autowired
    public ItemApiController(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @RequestMapping("/api/getItemTableData")
    public String getItemTableData(@RequestParam(value = "size") Integer size,
                                   @RequestParam(value = "pageNum") Integer pageNum,
                                   @RequestParam(value = "searchValue") String searchValue) {
        Page<Items> output;
        if (searchValue.isEmpty())
            output = itemsRepository.findAll(PageRequest.of(pageNum, size, Sort.Direction.ASC, "name"));
        else
            output = itemsRepository.findByFilter(PageRequest.of(pageNum, size, Sort.Direction.ASC, "name"), searchValue);

        return new Gson().toJson(fillItemData(output));
    }

    private List<HashMap<String, String>> fillItemData(Page<Items> e) {
        List<HashMap<String, String>> outList= new ArrayList<>();
        e.forEach(element->{
            HashMap<String, String> map = new HashMap<>();
            map.put("itemName", element.getName());
            map.put("itemCode", element.getCode());
            map.put("price", element.getPrice().toString());
            map.put("secondPrice", element.getSecondPrice().toString());
            map.put("action", element.getAction());
            map.put("lastUpdate", element.getLastUpdated()==null?EMPTY_STRING:element.getLastUpdated().toString());
            String value = element.getEslsById().size()==0?"??????":String.valueOf(getIdEslsById(element));
            map.put("associate", value);
            outList.add(map);});
        return outList;
    }

    private String getIdEslsById(Items element) {
        Collection<Esls> eslsById = element.getEslsById();
        //todo ???????????? ???????????? ???????????? ???????? ???? ?????????????? ??????????????
        return eslsById.stream().map(Esls::getCode).collect(joining(", "));
    }
}