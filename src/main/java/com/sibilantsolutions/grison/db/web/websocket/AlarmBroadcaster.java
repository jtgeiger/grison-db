package com.sibilantsolutions.grison.db.web.websocket;

import com.sibilantsolutions.grison.db.web.dto.AlarmStatusDto;

public interface AlarmBroadcaster {

    void broadcast(AlarmStatusDto alarmStatusDto);

}
