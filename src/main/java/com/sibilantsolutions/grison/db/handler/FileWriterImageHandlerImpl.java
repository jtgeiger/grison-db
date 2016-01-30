package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.db.model.CamImage;
import com.sibilantsolutions.grison.db.repository.CamImageRepository;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class FileWriterImageHandlerImpl implements ImageHandlerI {

    @Autowired
    CamImageRepository camImageRepository;

    @Autowired
    CamSessionHolder camSessionHolder;

    @Autowired
    private FileWriterParams imageFileWriterParams;

    private long curTimestamp = Long.MIN_VALUE;
    private int curMsRepeatCounter = Integer.MIN_VALUE;

    @Override
    public void onReceive(VideoDataText vdt) {
        long now = System.currentTimeMillis();
        byte[] dataContent = vdt.getDataContent();
        long timestampMs = vdt.getTimestampMs();

        if (timestampMs == curTimestamp) {
            curMsRepeatCounter++;
        }
        else {
            curMsRepeatCounter = 1;
            curTimestamp = timestampMs;
        }

        long sessionDbId = camSessionHolder.getCamSession().getId();

        String filename = "" + sessionDbId + '_' + timestampMs + '_' + (curMsRepeatCounter < 10 ? "0" : "") + curMsRepeatCounter + ".jpg";

        File file = new File(imageFileWriterParams.getParentDir(), filename);

        if (file.exists()) {
            throw new RuntimeException("file " + filename + " already exists.");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {

            fos.write(dataContent);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CamImage camImage = new CamImage(filename, new Timestamp(timestampMs), new Timestamp(now), vdt.getUptimeMs(), camSessionHolder.getCamSession());
        camImageRepository.save(camImage);
    }

    @Override
    public void onVideoStopped(VideoStoppedEvt arg0) {
        //No-op.
    }

}
