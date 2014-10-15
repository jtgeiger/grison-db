package com.sibilantsolutions.grison.db.dao;

import java.sql.Timestamp;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;
import com.sibilantsolutions.grison.driver.foscam.domain.Version;

public interface CamDao
{

    void alarm( AlarmTypeE alarmType, Timestamp timestamp, Number sessionDbId );

    Number sessionConnected( String cameraId, Version firmwareVersion, Timestamp timestamp );

}
