package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.AlarmDto;

public interface AlarmBroadcaster {

    void broadcast(AlarmDto alarmDto);

}
