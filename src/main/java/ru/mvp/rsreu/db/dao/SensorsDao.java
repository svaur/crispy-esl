package ru.mvp.rsreu.db.dao;

import ru.mvp.rsreu.db.entity.Sensors;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public interface SensorsDao {

    List<Sensors> getAll();

}
