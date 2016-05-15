package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;

public interface CamSessionStatusBroadcaster {

    void broadcast(CamSessionDto camSessionDto);

}
