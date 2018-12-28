package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.TaskResults;

@Repository
public interface TaskResultsRepository extends PagingAndSortingRepository<TaskResults, Long> {
}
