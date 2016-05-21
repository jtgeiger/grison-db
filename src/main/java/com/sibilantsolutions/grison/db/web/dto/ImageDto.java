package com.sibilantsolutions.grison.db.web.dto;

import java.util.Date;

public class ImageDto {

    private final int len;
    private Date timestamp;
    private byte[] imageBytes;

    public ImageDto(Date timestamp, int len, byte[] imageBytes) {
        this.timestamp = timestamp;
        this.len = len;
        this.imageBytes = imageBytes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public int getLen() {
        return len;
    }

}
