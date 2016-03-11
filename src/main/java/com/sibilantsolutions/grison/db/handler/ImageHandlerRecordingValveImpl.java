package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageHandlerRecordingValveImpl implements ImageRecordingValve {

    private boolean isRecordingEnabled = true;
    private final ImageHandlerI decorated;

    @Autowired
    public ImageHandlerRecordingValveImpl(ImageHandlerI decorated) {
        this.decorated = decorated;
    }

    @Override
    public boolean isRecordingEnabled() {
        return isRecordingEnabled;
    }

    @Override
    public void setRecordingEnabled(boolean isRecordingEnabled) {
        this.isRecordingEnabled = isRecordingEnabled;
    }

    @Override
    public void onReceive(VideoDataText videoData) {
        if (isRecordingEnabled()) {
            decorated.onReceive(videoData);
        }
    }

    @Override
    public void onVideoStopped(VideoStoppedEvt videoStoppedEvt) {
        decorated.onVideoStopped(videoStoppedEvt);
    }

}
