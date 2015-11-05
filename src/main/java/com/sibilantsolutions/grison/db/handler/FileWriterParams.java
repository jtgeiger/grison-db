package com.sibilantsolutions.grison.db.handler;

import java.io.File;

public class FileWriterParams {

    private final File parentDir;

    public FileWriterParams(File parentDir) {
        this.parentDir = parentDir;
        if (this.parentDir != null) {
            if (!this.parentDir.exists()) {
                boolean success = this.parentDir.mkdirs();
                if (!success) {
                    throw new RuntimeException("Failed to create parentDir from file=" + this.parentDir);
                }
            }
        }
    }

    public File getParentDir() {
        return parentDir;
    }

}
