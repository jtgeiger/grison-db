package com.sibilantsolutions.grison.db.handler;

import com.sibilantsolutions.grison.driver.foscam.domain.AudioDataText;
import com.sibilantsolutions.grison.evt.AudioHandlerI;
import com.sibilantsolutions.grison.evt.AudioStoppedEvt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileWriterAudioHandlerImpl implements AudioHandlerI {

    @Autowired
    CamSessionHolder camSessionHolder;

    @Autowired
    File audioFileDir;

    private long curTimestamp = Long.MIN_VALUE;
    private int curMsRepeatCounter = Integer.MIN_VALUE;

    @Override
    public void onAudioStopped(AudioStoppedEvt audioStoppedEvt) {
        //No-op.
    }

    @Override
    public void onReceive(AudioDataText audioData) {
        long now = System.currentTimeMillis();
        byte[] dataContent = audioData.getDataContent();
        long timestampMs = audioData.getTimestampMs();

        if (timestampMs == curTimestamp) {
            curMsRepeatCounter++;
        }
        else {
            curMsRepeatCounter = 1;
            curTimestamp = timestampMs;
        }

        long sessionDbId = camSessionHolder.getCamSession().getId();

        String filename = "" + sessionDbId + '_' + timestampMs + '_' + (curMsRepeatCounter < 10 ? "0" : "") + curMsRepeatCounter + '.' + audioData.getAudioFormat().toString().toLowerCase();

        File file = new File(audioFileDir, filename);

        if (file.exists()) {
            throw new RuntimeException("file " + filename + " already exists.");
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {

            fos.write(dataContent);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        camImageDao.insertImage(vdt, now, filename, sessionDbId);
    }

}
