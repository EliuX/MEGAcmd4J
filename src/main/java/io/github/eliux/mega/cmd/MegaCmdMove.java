package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.platform.OSPlatform;

public class MegaCmdMove extends AbstractMegaCmdRunnerWithParams {

    private final String sourceRemotePath;

    private final String remoteTarget;

    public MegaCmdMove(String sourceRemotePath, String remoteTarget) {
        this.sourceRemotePath = sourceRemotePath;
        this.remoteTarget = remoteTarget;
    }

    @Override
    String cmdParams() {
        final String localPath = MegaUtils.parseRemotePath(sourceRemotePath);
        final String remotePath = MegaUtils.parseRemotePath(remoteTarget);
        return String.format("%s %s", localPath, remotePath);
    }

    @Override
    public String getCmd() {
        return "mv";
    }

    public String getSourceRemotePath() {
        return sourceRemotePath;
    }

    public String getRemoteTarget() {
        return remoteTarget;
    }
}
