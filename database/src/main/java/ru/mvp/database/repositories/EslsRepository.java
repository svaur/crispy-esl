package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Esls;

@Repository
public interface EslsRepository extends JpaRepository<Esls, Long> {
    @Query("SELECT esl FROM Esls esl WHERE LOWER(esl.code) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(esl.eslType) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(esl.firmware) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(esl.status) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<Esls> findByFilter(Pageable pageable, String filter);
    @Query("SELECT esl FROM Esls esl where esl.code = ?1")
    Esls findByCode(String code);
    @Query("SELECT esl FROM Esls esl where esl.code = ?1 and esl.eslType = ?2 and esl.firmware = ?3")
    Esls findDuplicate(String code, String type, String firmware);
}
