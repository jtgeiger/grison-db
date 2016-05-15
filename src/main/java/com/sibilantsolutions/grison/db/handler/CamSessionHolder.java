package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;

public interface CamSessionHolder {

    void setCamSession(CamSession camSession);

    CamSession getCamSession();

    void setCamSessionDto(CamSessionDto camSessionDto);

    CamSessionDto getCamSessionDto();

}
