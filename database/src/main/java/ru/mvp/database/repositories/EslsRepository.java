package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Esls;

@Repository
public interface EslsRepository extends PagingAndSortingRepository<Esls, Long> {
}
