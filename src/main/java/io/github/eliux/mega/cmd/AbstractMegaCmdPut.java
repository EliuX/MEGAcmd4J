package io.github.eliux.mega.cmd;

import java.util.Optional;

public abstract class MegaCmdPut extends AbstractMegaCmdWithParams {

    private boolean remoteFolderCreatedIfNotPresent;

    private boolean uploadQueued;

    private boolean isQuotaWarningIgnored;

    @Override
    String cmdParams() {
        StringBuilder cmdParamsBuilder = new StringBuilder();

        if (remoteFolderCreatedIfNotPresent) {
            cmdParamsBuilder.append("-c ");
        }

        if (uploadQueued) {
            cmdParamsBuilder.append("-q ");
        }

        if (isQuotaWarningIgnored) {
            cmdParamsBuilder.append("--ignore-quota-warn ");
        }

        cmdParamsBuilder.append(cmdFileParams());

        return cmdParamsBuilder.toString();
    }

    public String getCmd() {
        return "mega-put";
    }

    @Override
    protected Optional executeSysCmd(String cmdStr) {
        return Optional.empty();    //TODO
    }

    public MegaCmdPut createRemoteIfNotPresent() {
        remoteFolderCreatedIfNotPresent = true;
        return this;
    }

    public MegaCmdPut dontCreateRemoteIfNotPresent() {
        remoteFolderCreatedIfNotPresent = false;
        return this;
    }

    public boolean isRemoteFolderCreatedIfNotPresent() {
        return remoteFolderCreatedIfNotPresent;
    }

    public MegaCmdPut queueUpload() {
        uploadQueued = true;
        return this;
    }

    public MegaCmdPut dontQueueUpload() {
        uploadQueued = false;
        return this;
    }

    public boolean isUploadQueued() {
        return uploadQueued;
    }

    public MegaCmdPut ignoreQuotaSurpassingWarning() {
        this.isQuotaWarningIgnored = true;
        return this;
    }

    public MegaCmdPut dontIgnoreQuotaSurpassingWarning() {
        this.isQuotaWarningIgnored = false;
        return this;
    }

    public boolean isQuotaWarningIgnored() {
        return isQuotaWarningIgnored;
    }

    protected abstract String cmdFileParams();
}
