package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Optional;

public class MegaCmdPutSingle extends AbstractMegaCmdPut {

    private final String localFile;

    private Optional<String> remotePath;

    public MegaCmdPutSingle(String localFile) {
        this.localFile = localFile;
        this.remotePath = Optional.empty();
    }

    public MegaCmdPutSingle(String localFile, String remotePath) {
        this.localFile = localFile;
        this.remotePath = Optional.of(remotePath);
    }

    protected String cmdFileParams() {
        final String localFile = OSPlatform.getCurrent()
                .parseLocalPath(getLocalFile());
        StringBuilder cmdFileParamsBuilder = new StringBuilder(localFile);

        getRemotePath().map(MegaUtils::parseRemotePath)
                .map(" "::concat)
                .ifPresent(cmdFileParamsBuilder::append);

        return cmdFileParamsBuilder.toString();
    }

    public String getLocalFile() {
        return localFile;
    }

    public Optional<String> getRemotePath() {
        return remotePath;
    }

    public MegaCmdPutSingle setRemotePath(String remotePath) {
        this.remotePath = Optional.of(remotePath);
        return this;
    }
}
