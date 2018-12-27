package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Tasks;

@Repository
public interface TasksRepository extends PagingAndSortingRepository<Tasks, Long> {
}
