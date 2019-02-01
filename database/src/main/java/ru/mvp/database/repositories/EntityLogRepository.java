package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.EntityLog;

@Repository
public interface EntityLogRepository extends JpaRepository<EntityLog, Long> {
    @Query("SELECT entityLog FROM EntityLog entityLog WHERE LOWER(entityLog.name) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(entityLog.source) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(entityLog.type) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(entityLog.event) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<EntityLog> findByFilter(Pageable pageable, String filter);
}
