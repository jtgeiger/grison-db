package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.CamSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class CamSessionStatusBroadcasterImpl implements CamSessionStatusBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    CamSessionStatusBroadcasterImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcast(CamSessionDto camSessionDto) {
        messagingTemplate.convertAndSend("/topic/sessions", camSessionDto);
    }

}
