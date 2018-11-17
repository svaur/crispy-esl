package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Item;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ItemDao {
    List<Item> getAll();
    void insertOrUpdateItems(List<Item> itemList);
}
