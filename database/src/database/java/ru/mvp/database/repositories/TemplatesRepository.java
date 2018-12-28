package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Templates;

@Repository
public interface TemplatesRepository extends PagingAndSortingRepository<Templates, Long> {

}