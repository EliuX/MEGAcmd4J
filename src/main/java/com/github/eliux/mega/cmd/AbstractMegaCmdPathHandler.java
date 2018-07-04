package com.github.eliux.mega.cmd;

public abstract class AbstractMegaCmdPathHandler extends AbstractMegaCmdRunnerWithParams {

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

  public <R extends AbstractMegaCmdPathHandler> R createRemoteIfNotPresent() {
    remoteFolderCreatedIfNotPresent = true;
    return (R) this;
  }

  public <R extends AbstractMegaCmdPathHandler> R skipIfRemoteNotPresent() {
    remoteFolderCreatedIfNotPresent = false;
    return (R) this;
  }

  public boolean isRemoteFolderCreatedIfNotPresent() {
    return remoteFolderCreatedIfNotPresent;
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
