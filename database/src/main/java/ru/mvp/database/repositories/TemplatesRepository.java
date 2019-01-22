package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.Templates;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, Long> {

}