package com.sibilantsolutions.grison.db.model;

import com.sibilantsolutions.grison.driver.foscam.domain.AlarmTypeE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class CamAlarm {

    @Id
    @GeneratedValue
    long id;

    AlarmTypeE alarmType;
    Timestamp alarmTime;

    @ManyToOne
    @JoinColumn(name = "camSession", updatable = false, insertable = false)
    CamSession camSession;

    @Override
    public String toString() {
        return "CamAlarm{" + "id=" + id +
                ", alarmType=" + alarmType +
                ", alarmTime=" + alarmTime +
                ", camSession=" + camSession +
                '}';
    }

}
