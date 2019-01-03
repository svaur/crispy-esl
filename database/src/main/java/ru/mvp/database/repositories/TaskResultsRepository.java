package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.TaskResults;

@Repository
public interface TaskResultsRepository extends JpaRepository<TaskResults, Long> {
}
