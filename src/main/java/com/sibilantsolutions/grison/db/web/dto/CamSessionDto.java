package com.sibilantsolutions.grison.db.web.dto;

import com.sibilantsolutions.grison.db.persistence.entity.CamSession;

public class CamSessionDto {

    private CamSession camSession;
    private StreamStatusDto streamStatusDto;

    public CamSessionDto(CamSession camSession, StreamStatusDto streamStatusDto) {
        this.camSession = camSession;
        this.streamStatusDto = streamStatusDto;
    }

    public CamSession getCamSession() {
        return camSession;
    }

    public StreamStatusDto getStreamStatusDto() {
        return streamStatusDto;
    }

}
