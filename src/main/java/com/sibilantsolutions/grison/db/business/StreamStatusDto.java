package com.sibilantsolutions.grison.db.business;

public class StreamStatusDto {

    private boolean isConnected;
    private boolean isVideoStarted;
    private boolean isAudioStarted;

    public StreamStatusDto(boolean isConnected, boolean isVideoStarted, boolean isAudioStarted) {

        this.isConnected = isConnected;
        this.isVideoStarted = isVideoStarted;
        this.isAudioStarted = isAudioStarted;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isVideoStarted() {
        return isVideoStarted;
    }

    public boolean isAudioStarted() {
        return isAudioStarted;
    }

}
