package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.ItemParams;

@Repository
public interface ItemParamsRepository extends PagingAndSortingRepository<ItemParams, Long> {
}
