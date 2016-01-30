package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;

public interface CamSessionHolder {

    void setCamSession(CamSession camSession);

    CamSession getCamSession();

}
