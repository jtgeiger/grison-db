package com.sibilantsolutions.grison.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

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
