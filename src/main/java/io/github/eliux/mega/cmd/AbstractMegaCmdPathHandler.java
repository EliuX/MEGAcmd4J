package io.github.eliux.mega.cmd;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMegaCmdPathHandler extends AbstractMegaCmdRunnerWithParams {

    private boolean remotePathCreatedIfNotPresent;

    private boolean uploadQueued;

    private boolean isQuotaWarningIgnored = true;

    @Override
    List<String> cmdParams() {
        List<String> cmdParams = new LinkedList<>();

        if (remotePathCreatedIfNotPresent) {
            cmdParams.add("-c");
        }

        if (uploadQueued) {
            cmdParams.add("-q");
        }

        if (isQuotaWarningIgnored) {
            cmdParams.add("--ignore-quota-warn");
        }

        cmdParams.addAll(cmdFileParams());

        return cmdParams;
    }

    public <R extends AbstractMegaCmdPathHandler> R createRemotePathIfNotPresent() {
        remotePathCreatedIfNotPresent = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler> R skipIfRemotePathNotPresent() {
        remotePathCreatedIfNotPresent = false;
        return (R) this;
    }

    public boolean isRemotePathCreatedIfNotPresent() {
        return remotePathCreatedIfNotPresent;
    }

    public <R extends AbstractMegaCmdPathHandler> R queueUpload() {
        uploadQueued = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler> R waitToUpload() {
        uploadQueued = false;
        return (R) this;
    }

    public boolean isUploadQueued() {
        return uploadQueued;
    }

    public <R extends AbstractMegaCmdPathHandler> R ignoreQuotaSurpassingWarning() {
        this.isQuotaWarningIgnored = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler> R warnQuotaSurpassing() {
        this.isQuotaWarningIgnored = false;
        return (R) this;
    }

    public boolean isQuotaWarningIgnored() {
        return isQuotaWarningIgnored;
    }

    protected abstract List<String> cmdFileParams();
}
