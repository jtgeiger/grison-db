package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.AlarmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlarmBroadcasterImpl implements AlarmBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public AlarmBroadcasterImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcast(AlarmDto alarmDto) {
        messagingTemplate.convertAndSend("/topic/alarms", alarmDto);
    }

}
