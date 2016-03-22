package com.sibilantsolutions.grison.db.persistence.repository;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;

import java.util.List;

interface CamSessionRepositoryCustom {

    List<CamSession> findByAllConnected();

}
