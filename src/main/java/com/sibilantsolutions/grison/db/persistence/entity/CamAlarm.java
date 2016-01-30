package com.sibilantsolutions.grison.db.persistence.entity;

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
    @JoinColumn(name = "camSession")
    CamSession camSession;

    CamAlarm() {/*No-op*/}

    public CamAlarm(AlarmTypeE alarmType, Timestamp alarmTime, CamSession camSession) {
        this.alarmType = alarmType;
        this.alarmTime = alarmTime;
        this.camSession = camSession;
    }

    public long getId() {
        return id;
    }

    public AlarmTypeE getAlarmType() {
        return alarmType;
    }

    public Timestamp getAlarmTime() {
        return alarmTime;
    }

    public CamSession getCamSession() {
        return camSession;
    }

    @Override
    public String toString() {
        return "CamAlarm{" + "id=" + id +
                ", alarmType=" + alarmType +
                ", alarmTime=" + alarmTime +
                ", camSession=" + camSession +
                '}';
    }

}
