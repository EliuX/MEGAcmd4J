package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Arrays;
import java.util.List;

public class MegaCmdMove extends AbstractMegaCmdRunnerWithParams {

    private final String sourceRemotePath;

    private final String remoteTarget;

    public MegaCmdMove(String sourceRemotePath, String remoteTarget) {
        this.sourceRemotePath = sourceRemotePath;
        this.remoteTarget = remoteTarget;
    }

    @Override
    List<String> cmdParams() {
        return Arrays.asList(sourceRemotePath, remoteTarget);
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
