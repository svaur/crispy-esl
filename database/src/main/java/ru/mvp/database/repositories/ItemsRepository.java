package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("SELECT item FROM Items item WHERE LOWER(item.name) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(item.code) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<Items> findByFilter(Pageable pageable, String filter);
}
