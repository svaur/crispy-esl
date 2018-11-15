package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ESLDao {

    List<ESL> getAll();
    List<ESL> getAll(int limit);
    /* Поиск по колонкам: EslCode, EslType, ItemCode, ItemType */
    List<ESL> searchByValue(String value, int showSize);
    ESL searchByESLCode(String eslCode);
    Item searchByItemCode(String itemCode);
}
