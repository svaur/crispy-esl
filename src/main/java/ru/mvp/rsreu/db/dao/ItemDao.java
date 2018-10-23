package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Item;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ItemDao {
    /* Минимум для показухи */
    List<Item> getAll();
    Item getByESLCode(int eslId);
}
