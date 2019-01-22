package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.AvailableParamsForTemplate;

@Repository
public interface AvailableParamsForTemplateRepository extends JpaRepository<AvailableParamsForTemplate, Long> {
}
