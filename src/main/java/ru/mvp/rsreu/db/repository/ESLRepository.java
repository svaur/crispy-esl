package ru.mvp.rsreu.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mvp.rsreu.db.entity.ESL;

public interface ESLRepository extends JpaRepository<ESL, String> {
   ESL findByEslCode(String eslCode);
}