package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.ItemParamsGroup;

@Repository
public interface ItemParamsGroupRepository extends PagingAndSortingRepository<ItemParamsGroup, Long> {
}
