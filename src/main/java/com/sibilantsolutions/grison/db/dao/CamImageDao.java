package com.sibilantsolutions.grison.db.dao;

import com.sibilantsolutions.grison.driver.foscam.domain.VideoDataText;

public interface CamImageDao {

    public Number insertImage(VideoDataText vdt, long now, String filename, Number sessionDbID);

}
