package ru.mvp.rsreu.integration.file.parsers;

import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.Item;

import java.util.List;

/**
 * Created by Art on 16.11.2018.
 */
public interface ItemParser<T> {
    List<Item> parse(T data);
}
