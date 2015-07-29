package com.sibilantsolutions.grison.db.handler;

import org.springframework.stereotype.Component;

@Component
public class SessionDbIdHolderImpl implements SessionDbIdHolderI {

    private Number sessionDbId;

    @Override
    public void setSessionDbId(Number sessionDbId) {
        this.sessionDbId = sessionDbId;
    }

    @Override
    public Number getSessionDbId() {
        return sessionDbId;
    }

}
