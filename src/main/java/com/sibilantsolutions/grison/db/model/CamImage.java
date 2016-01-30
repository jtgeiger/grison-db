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
    @JoinColumn(name = "camSession", updatable = false, insertable = false)
    CamSession camSession;

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
