package com.sibilantsolutions.grison.db.persistence.repository;

import com.sibilantsolutions.grison.db.handler.CamSessionHolder;
import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class CamSessionRepositoryImpl implements CamSessionRepositoryCustom {

    private static final Logger LOG = LoggerFactory.getLogger(CamSessionRepositoryImpl.class);

    private final CamSessionHolder camSessionHolder;

    @Autowired
    CamSessionRepositoryImpl(CamSessionHolder camSessionHolder) {
        LOG.info("\n\n\n Constructed {}.\n\n\n", getClass());
        this.camSessionHolder = camSessionHolder;
    }

    //TODO: How to get the custom method to show up in the available searches in HAL (/camSessions/search), as opposed
    //to wiring up a custom controller method and (presumably) having to manually add a link?
    @Override
    public List<CamSession> findByAllConnected() {
        return Collections.singletonList(camSessionHolder.getCamSession());
    }

}
