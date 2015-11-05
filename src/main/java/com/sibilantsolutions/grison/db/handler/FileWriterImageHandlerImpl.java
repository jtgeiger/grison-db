package com.sibilantsolutions.grison.db.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sibilantsolutions.grison.db.dao.CamImageDao;
import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;
import com.sibilantsolutions.grison.evt.ImageHandlerI;
import com.sibilantsolutions.grison.evt.VideoStoppedEvt;

@Component
public class FileWriterImageHandlerImpl implements ImageHandlerI {

    @Autowired
    private CamImageDao camImageDao;

    @Autowired
    private SessionDbIdHolderI sessionDbIdHolder;

    @Autowired
    private FileWriterParams fileWriterParams;

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

        Number sessionDbId = sessionDbIdHolder.getSessionDbId();

        String filename = "" + sessionDbId + '_' + timestampMs + '_' + (curMsRepeatCounter < 10 ? "0" : "") + curMsRepeatCounter + ".jpg";

        File file = new File(fileWriterParams.getParentDir(), filename);

        if (file.exists()) {
            throw new RuntimeException("file " + filename + " already exists.");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {

            fos.write(dataContent);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        camImageDao.insertImage(vdt, now, filename, sessionDbId);
    }

    @Override
    public void onVideoStopped(VideoStoppedEvt arg0) {
        //No-op.
    }

}
