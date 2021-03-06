package com.sibilantsolutions.grison.db.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class CamSession {

    @Id
    @GeneratedValue
    long id;

    String cameraId;

    int firmwareVersionMajor;
    int firmwareVersionMinor;
    int firmwareVersionPatch;
    int firmwareVersionBuild;

    Timestamp connectTime;

    @OneToMany(mappedBy = "camSession")
    Set<CamAlarm> alarms;

    @OneToMany(mappedBy = "camSession")
    Set<CamImage> images;

    CamSession() {/*No-op*/}

    public CamSession(String cameraId, int firmwareVersionMajor, int firmwareVersionMinor, int firmwareVersionPatch, int firmwareVersionBuild, Timestamp connectTime) {
        this.cameraId = cameraId;
        this.firmwareVersionMajor = firmwareVersionMajor;
        this.firmwareVersionMinor = firmwareVersionMinor;
        this.firmwareVersionPatch = firmwareVersionPatch;
        this.firmwareVersionBuild = firmwareVersionBuild;
        this.connectTime = connectTime;
    }

    public long getId() {
        return id;
    }

    public String getCameraId() {
        return cameraId;
    }

    public int getFirmwareVersionMajor() {
        return firmwareVersionMajor;
    }

    public int getFirmwareVersionMinor() {
        return firmwareVersionMinor;
    }

    public int getFirmwareVersionPatch() {
        return firmwareVersionPatch;
    }

    public int getFirmwareVersionBuild() {
        return firmwareVersionBuild;
    }

    public Timestamp getConnectTime() {
        return connectTime;
    }

    public Set<CamAlarm> getAlarms() {
        return alarms;
    }

    public Set<CamImage> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "CamSession{" + "id=" + id +
                ", cameraId='" + cameraId + '\'' +
                ", firmwareVersionMajor=" + firmwareVersionMajor +
                ", firmwareVersionMinor=" + firmwareVersionMinor +
                ", firmwareVersionPatch=" + firmwareVersionPatch +
                ", firmwareVersionBuild=" + firmwareVersionBuild +
                ", connectTime=" + connectTime +
                '}';
    }

}
