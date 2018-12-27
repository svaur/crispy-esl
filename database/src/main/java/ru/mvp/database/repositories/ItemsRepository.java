package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Items;

@Repository
public interface ItemsRepository extends PagingAndSortingRepository<Items, Long> {
}
