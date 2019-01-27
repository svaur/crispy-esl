package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Tasks;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    @Query("SELECT task FROM Tasks task WHERE LOWER(task.taskName) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(task.cronExpression) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<Tasks> findByFilter(Pageable pageable, String filter);
    List<Tasks> findAllByStatusAndStartDateBefore(Integer status, Timestamp StartDate);
}
