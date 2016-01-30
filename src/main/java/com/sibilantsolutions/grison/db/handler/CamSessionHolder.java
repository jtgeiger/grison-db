package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.model.CamSession;

public interface CamSessionHolder {

    void setCamSession(CamSession camSession);

    CamSession getCamSession();

}
