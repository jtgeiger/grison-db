package com.sibilantsolutions.grison.db.web.dto;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;

import java.util.Date;

public class AlarmStatusDto {

    private final AlarmTypeE alarmType;
    private final Date timestamp;

    public AlarmStatusDto(AlarmTypeE alarmType, Date timestamp) {
        this.alarmType = alarmType;
        this.timestamp = timestamp;
    }

    public AlarmTypeE getAlarmType() {
        return alarmType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
