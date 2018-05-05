package io.github.eliux.mega.cmd;

import java.util.Optional;

public class MegaCmdPutSingle extends AbstractMegaCmdPut {

    private final String localFile;

    private Optional<String> remotePath;

    public MegaCmdPutSingle(String localFile){
        this.localFile = localFile;
        this.remotePath = Optional.empty();
    }

    public MegaCmdPutSingle(String localFile, String remotePath) {
        this.localFile = localFile;
        this.remotePath = Optional.of(remotePath);
    }

    protected String cmdFileParams(){
        StringBuilder cmdFileParamsBuilder =
                new StringBuilder(getLocalFile());

        getRemotePath().ifPresent(
                x -> cmdFileParamsBuilder.append(" ".concat(x))
        );

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
