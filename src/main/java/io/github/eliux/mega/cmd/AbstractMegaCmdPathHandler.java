package io.github.eliux.mega.cmd;

public abstract class AbstractMegaCmdPathHandler<T> extends AbstractMegaCmdWithParams<T> {

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

    public <R extends AbstractMegaCmdPathHandler<T>> R createRemoteIfNotPresent() {
        remoteFolderCreatedIfNotPresent = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler<T>> R createRemoteOnlyIfPresent() {
        remoteFolderCreatedIfNotPresent = false;
        return (R) this;
    }

    public boolean isRemoteFolderCreatedIfNotPresent() {
        return remoteFolderCreatedIfNotPresent;
    }

    public <R extends AbstractMegaCmdPathHandler<T>> R queueUpload() {
        uploadQueued = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler<T>> R waitToUpload() {
        uploadQueued = false;
        return (R) this;
    }

    public boolean isUploadQueued() {
        return uploadQueued;
    }

    public <R extends AbstractMegaCmdPathHandler<T>> R ignoreQuotaSurpassingWarning() {
        this.isQuotaWarningIgnored = true;
        return (R) this;
    }

    public <R extends AbstractMegaCmdPathHandler<T>> R warnQuotaSurpassing() {
        this.isQuotaWarningIgnored = false;
        return (R) this;
    }

    public boolean isQuotaWarningIgnored() {
        return isQuotaWarningIgnored;
    }

    protected abstract String cmdFileParams();
}
