package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;

public class ImportInfo {

    private String remotePath;

    public ImportInfo(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public static ImportInfo parseImportInfo(String importInfoStr) {
        try {
            if (importInfoStr.contains("Imported folder complete:")) {
                final String[] tokens = importInfoStr.split(": ");
                return new ImportInfo(tokens[1]);
            }
        } catch (Exception e) {
            throw new MegaInvalidResponseException(e.getMessage());
        }

        throw new MegaInvalidResponseException(importInfoStr);
    }
}
