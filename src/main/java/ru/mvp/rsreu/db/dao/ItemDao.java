package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Item;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ItemDao {
    /* Минимум для показухи */
    List<Item> getAll();
    List<Item> getAll(int limit);
    /* Поиск по колонкам: ItemCode, ItemType */
    List<Item> searchByValue(String value, int showSize);
    Item searchByItemCode(String itemCode);
}
