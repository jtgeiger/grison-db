package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;
import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;
import org.springframework.stereotype.Component;

@Component
public class CamSessionHolderImpl implements CamSessionHolder {

    private CamSession camSession;
    private CamSessionDto camSessionDto;

    @Override
    public void setCamSession(CamSession camSession) {
        this.camSession = camSession;
    }

    @Override
    public CamSession getCamSession() {
        return this.camSession;
    }

    @Override
    public void setCamSessionDto(CamSessionDto camSessionDto) {
        this.camSessionDto = camSessionDto;
    }

    @Override
    public CamSessionDto getCamSessionDto() {
        return camSessionDto;
    }

}
