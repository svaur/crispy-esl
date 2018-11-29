package ru.mvp.rsreu.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mvp.rsreu.db.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
    Item findByItemCode(String itemCode);
}
