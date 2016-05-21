package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;
import com.sibilantsolutions.grison.db.web.dto.ImageDto;

public interface CamSessionStatusBroadcaster {

    void broadcast(CamSessionDto camSessionDto);

    void broadcast(ImageDto imageDto);

}
