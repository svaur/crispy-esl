package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Merchandise;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface MerchandiseDao {
    /* Минимум для показухи */
    List<Merchandise> getAll();
}
