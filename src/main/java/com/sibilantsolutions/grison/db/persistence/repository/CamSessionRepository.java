package com.sibilantsolutions.grison.db.persistence.repository;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamSessionRepository extends JpaRepository<CamSession, Long> {
}
