package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("item")
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public List<Item> searchByValue(String value, Pageable pageable) {
        List<Item> fullList = itemRepository.findAll();
        List<Item> resultList = new ArrayList<>(pageable.getPageSize());
        Iterator<Item> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < pageable.getPageSize()) {
            Item tempItem = iterator.next();
            if (tempItem.getItemCode().contains(value) || tempItem.getItemName().toLowerCase().contains(value.toLowerCase())) {
                resultList.add(tempItem);
                i++;
            }
        }
        return resultList;
    }

    public Item searchByItemCode(String itemCode) {
      return itemRepository.findByItemCode(itemCode);
    }

    public void insertOrUpdateItems(List<Item> itemList) {
        for (Item item: itemList) {
            if (!itemRepository.exists(Example.of(item))) {
                itemRepository.save(item);
            }
        }
    }
}
