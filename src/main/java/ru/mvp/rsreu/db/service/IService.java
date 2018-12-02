package ru.mvp.rsreu.db.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface IService<T> {

    Page<T> findAll(Pageable pageable);

    List<T> searchByValue(String value, Pageable pageable);

    HashMap<String, String> fillEntityData(T element);
}
