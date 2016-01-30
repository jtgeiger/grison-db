package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import org.springframework.stereotype.Component;

@Component
public class CamSessionHolderImpl implements CamSessionHolder {

    private CamSession camSession;

    @Override
    public void setCamSession(CamSession camSession) {
        this.camSession = camSession;
    }

    @Override
    public CamSession getCamSession() {
        return this.camSession;
    }

}
