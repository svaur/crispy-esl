package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.AvailableParamsForTemplate;

@Repository
public interface AvailableParamsForTemplateRepository extends PagingAndSortingRepository<AvailableParamsForTemplate, Long> {
}
