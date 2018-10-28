package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.ESL;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface ESLDao {

    List<ESL> getAll();
    ESL searchByESLCode(String eslCode);
    /* Длинно неказисто, зато понятно) */
    List<ESL> searchByESLCodeOrTypeOrItemCodeOrItemName(String value);

}
