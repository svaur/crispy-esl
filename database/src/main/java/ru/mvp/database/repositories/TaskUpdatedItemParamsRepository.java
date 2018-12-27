package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.TaskUpdatedItemParams;

@Repository
public interface TaskUpdatedItemParamsRepository extends PagingAndSortingRepository<TaskUpdatedItemParams, Long> {
}
