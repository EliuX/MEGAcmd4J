package io.github.eliux.mega.cmd.cmd;

import java.util.Optional;

public class MegaCmdGet extends AbstractMegaCmdPathHandler {

    private String remotePath;

    private Optional<String> localPath;

    public MegaCmdGet(String remotePath) {
        this.remotePath = remotePath;
        this.localPath = Optional.empty();
    }

    public MegaCmdGet(String remotePath, String localPath) {
        this.remotePath = remotePath;
        this.localPath = Optional.of(localPath);
    }

    public String getRemotePath() {
        return remotePath;
    }

    public MegaCmdGet setRemotePath(String remotePath) {
        this.remotePath = remotePath;
        return this;
    }

    public Optional<String> getLocalPath() {
        return localPath;
    }

    public MegaCmdGet setLocalPath(String localPath) {
        this.localPath = Optional.of(localPath);
        return this;
    }

    public MegaCmdGet useCurrentFolder() {
        this.localPath = Optional.empty();
        return this;
    }

    @Override
    public String getCmd() {
        return "get";
    }

    @Override
    protected String cmdFileParams() {
        StringBuilder cmdFileParamsBuilder =
                new StringBuilder(getRemotePath());

        getLocalPath().ifPresent(
                x -> cmdFileParamsBuilder.append(" ".concat(x))
        );

        return cmdFileParamsBuilder.toString();
    }
}
