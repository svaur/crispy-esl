package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.ItemParamsGroup;

@Repository
public interface ItemParamsGroupRepository extends JpaRepository<ItemParamsGroup, Long> {
}
