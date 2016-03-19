package com.sibilantsolutions.grison.db.persistence.repository;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CamSessionRepository extends JpaRepository<CamSession, Long> {

    List<CamSession> findByConnectTimeGreaterThanEqual(@Param("start") Timestamp start);

}
