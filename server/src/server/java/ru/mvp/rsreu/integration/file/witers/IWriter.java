package ru.mvp.rsreu.integration.file.witers;

import ru.mvp.rsreu.db.entity.ESL;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by Art on 18.11.2018.
 */
public interface IWriter {
    //при необходимости хаюзать дженерики, пока нет смысла городить
    void write(List<ESL> eslList, Path path);
}
