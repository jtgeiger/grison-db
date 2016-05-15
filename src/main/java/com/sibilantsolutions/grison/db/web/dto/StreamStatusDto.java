package com.sibilantsolutions.grison.db.web.dto;

public class StreamStatusDto {

    private boolean isVideoStarted;
    private boolean isAudioStarted;

    public StreamStatusDto(boolean isVideoStarted, boolean isAudioStarted) {

        this.isVideoStarted = isVideoStarted;
        this.isAudioStarted = isAudioStarted;
    }

    public boolean isVideoStarted() {
        return isVideoStarted;
    }

    public boolean isAudioStarted() {
        return isAudioStarted;
    }

}
