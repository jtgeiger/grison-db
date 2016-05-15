package com.sibilantsolutions.grison.db.web.dto;

public class CamSessionDto {

    private final long cameraSessionId;
    private final boolean isConnected;
    private final AlarmStatusDto alarmStatus;
    private final StreamStatusDto streamStatus;

    public CamSessionDto(long cameraSessionId, boolean isConnected, StreamStatusDto streamStatus, AlarmStatusDto alarmStatus) {
        this.cameraSessionId = cameraSessionId;
        this.isConnected = isConnected;
        this.streamStatus = streamStatus;
        this.alarmStatus = alarmStatus;
    }

    public long getCameraSessionId() {
        return cameraSessionId;
    }

    public StreamStatusDto getStreamStatus() {
        return streamStatus;
    }

    public AlarmStatusDto getAlarmStatus() {
        return alarmStatus;
    }

    public boolean isConnected() {
        return isConnected;
    }

}
