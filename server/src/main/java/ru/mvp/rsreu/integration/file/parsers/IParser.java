package ru.mvp.rsreu.integration.file.parsers;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by Art on 16.11.2018.
 */
public interface IParser {
    //при необходимости хаюзать дженерики, пока нет смысла городить
    List<?> parse(Path data);
}
