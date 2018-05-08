package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

public class MegaCmdCopy extends AbstractMegaCmdProcedureWithParams {

    private final String sourceRemotePath;

    private final String remoteTarget;

    public MegaCmdCopy(String sourceRemotePath, String remoteTarget) {
        this.sourceRemotePath = sourceRemotePath;
        this.remoteTarget = remoteTarget;
    }

    @Override
    String cmdParams() {
        return String.format("%s %s", sourceRemotePath, remoteTarget);
    }

    @Override
    public String getCmd() {
        return "cp";
    }

    public String getSourceRemotePath() {
        return sourceRemotePath;
    }

    public String getRemoteTarget() {
        return remoteTarget;
    }

    public boolean isRemoteTargetAUser() {
        return MegaUtils.isEmail(remoteTarget);
    }

    public boolean isRemoteTargetADirectory() {
        return MegaUtils.isDirectory(remoteTarget);
    }
}
