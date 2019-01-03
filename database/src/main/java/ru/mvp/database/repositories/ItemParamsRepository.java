package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.ItemParams;

@Repository
public interface ItemParamsRepository extends JpaRepository<ItemParams, Long> {
}
