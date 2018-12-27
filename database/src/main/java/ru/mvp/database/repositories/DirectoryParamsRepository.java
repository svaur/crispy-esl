package ru.mvp.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.DirectoryParams;

@Repository
public interface DirectoryParamsRepository extends PagingAndSortingRepository<DirectoryParams, Long> {

}