package com.sibilantsolutions.grison.db.persistence.repository;

import com.sibilantsolutions.grison.db.persistence.entity.CamAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamAlarmRepository extends JpaRepository<CamAlarm, Long> {
}
