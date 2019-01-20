package io.github.eliux.mega.cmd;

import java.util.LinkedList;
import java.util.List;
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
    protected List<String> cmdFileParams() {
        List<String> cmdFileParams = new LinkedList<>();

        cmdFileParams.add(getRemotePath());

        getLocalPath().ifPresent(cmdFileParams::add);

        return cmdFileParams;
    }
}
