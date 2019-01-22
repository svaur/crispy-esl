package ru.mvp.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.DirectoryParams;

@Repository
public interface DirectoryParamsRepository extends JpaRepository<DirectoryParams, Long> {

}