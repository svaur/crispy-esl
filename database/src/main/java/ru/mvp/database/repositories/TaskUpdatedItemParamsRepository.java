package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.TaskUpdatedItemParams;

@Repository
public interface TaskUpdatedItemParamsRepository extends JpaRepository<TaskUpdatedItemParams, Long> {
}
