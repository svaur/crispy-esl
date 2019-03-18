package ru.mvp.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mvp.database.entities.AccessPointsInfo;
import ru.mvp.database.entities.ItemParamsGroup;

@Repository
public interface AccessPointsInfoRepository extends JpaRepository<AccessPointsInfo, Long> {
    @Query("SELECT accessPointsInfo FROM AccessPointsInfo accessPointsInfo WHERE LOWER(accessPointsInfo.ip) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(accessPointsInfo.port) LIKE LOWER(CONCAT('%',?1,'%'))")
    Page<AccessPointsInfo> findByFilter(Pageable pageable, String filter);
}
