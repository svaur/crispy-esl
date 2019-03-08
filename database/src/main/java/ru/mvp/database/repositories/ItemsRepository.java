package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Items;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("SELECT item FROM Items item WHERE LOWER(item.name) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(item.code) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<Items> findByFilter(Pageable pageable, String filter);
    @Query("SELECT item FROM Items item where item.code = ?1")
    Items findByCode(String code);
    @Query("SELECT item FROM Items item where item.code = ?1 and item.name = ?2 and item.price = ?3 and item.secondPrice = ?4 and item.storageUnit = ?5 and item.action = ?6")
    Items findDuplicate(String code, String name, Double price, Double secondPrice, String storageUnit, String action);
}
