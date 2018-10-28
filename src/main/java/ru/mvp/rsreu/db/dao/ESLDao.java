package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.ESL;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ESLDao {

    List<ESL> getAll();

    /* Поиск по колонкам: EslCode, EslType, ItemCode, ItemType */
    List<ESL> searchByValue(String value);

    ESL searchByESLCode(String eslCode);
}
