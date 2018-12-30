package io.github.eliux.mega.cmd;

public abstract class AbstractMegaCmdPathHandler extends AbstractMegaCmdRunnerWithParams {

    private boolean remotePathCreatedIfNotPresent;

    private boolean uploadQueued;

    private boolean isQuotaWarningIgnored;

    @Override
    String cmdParams() {
        StringBuilder cmdParamsBuilder = new StringBuilder();

        if (remotePathCreatedIfNotPresent) {
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

    protected abstract String cmdFileParams();
}
