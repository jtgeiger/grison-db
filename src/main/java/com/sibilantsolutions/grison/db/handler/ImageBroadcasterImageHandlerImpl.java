package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.web.dto.ImageDto;
import com.sibilantsolutions.grison.db.web.websocket.CamSessionStatusBroadcaster;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ImageBroadcasterImageHandlerImpl implements ImageHandlerI {

    private final CamSessionStatusBroadcaster camSessionStatusBroadcaster;

    @Autowired
    public ImageBroadcasterImageHandlerImpl(CamSessionStatusBroadcaster camSessionStatusBroadcaster) {
        this.camSessionStatusBroadcaster = camSessionStatusBroadcaster;
    }

    @Override
    public void onReceive(VideoDataText videoData) {
        ImageDto imageDto = new ImageDto(new Date(videoData.getTimestampMs()), videoData.getDataContent().length, videoData.getDataContent());
        camSessionStatusBroadcaster.broadcast(imageDto);
    }

    @Override
    public void onVideoStopped(VideoStoppedEvt videoStoppedEvt) {

    }
}
