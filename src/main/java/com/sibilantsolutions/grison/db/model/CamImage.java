package com.sibilantsolutions.grison.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class CamImage {

    @Id
    @GeneratedValue
    long id;

    String imageName;
    Timestamp camTimestamp;
    Timestamp serverTimestamp;
    long uptime;

    @ManyToOne
    @JoinColumn(name = "camSession")
    CamSession camSession;

    public CamImage(String imageName, Timestamp camTimestamp, Timestamp serverTimestamp, long uptime, CamSession camSession) {
        this.imageName = imageName;
        this.camTimestamp = camTimestamp;
        this.serverTimestamp = serverTimestamp;
        this.uptime = uptime;
        this.camSession = camSession;
    }

    public long getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public Timestamp getCamTimestamp() {
        return camTimestamp;
    }

    public Timestamp getServerTimestamp() {
        return serverTimestamp;
    }

    public long getUptime() {
        return uptime;
    }

    public CamSession getCamSession() {
        return camSession;
    }

    @Override
    public String toString() {
        return "CamImage{" + "id=" + id +
                ", imageName='" + imageName + '\'' +
                ", camTimestamp=" + camTimestamp +
                ", serverTimestamp=" + serverTimestamp +
                ", uptime=" + uptime +
                ", camSession=" + camSession +
                '}';
    }

}
