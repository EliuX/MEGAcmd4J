package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.LinkedList;
import java.util.List;
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

    protected List<String> cmdFileParams() {
        final List<String> cmdFileParams = new LinkedList<>();

        cmdFileParams.add(getLocalFile());

        getRemotePath().ifPresent(cmdFileParams::add);

        return cmdFileParams;
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
