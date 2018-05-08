package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MegaCmdList extends AbstractMegaCmdWithParams<List<FileInfo>> {

    private String remotePath;

    public MegaCmdList() {
        this.remotePath = "";
    }

    public MegaCmdList(String remotePath) {
        this.remotePath = remotePath;
    }

    @Override
    public String getCmd() {
        return "ls";
    }

    @Override
    String cmdParams() {
        return "-l " + remotePath;
    }

    @Override
    protected Optional<List<FileInfo>> executeSysCmd(String cmdStr) {
        try {
            final List<FileInfo> infoOfFiles = MegaUtils.execWithOutput(cmdStr)
                    .stream().skip(1)
                    .map(FileInfo::valueOf)
                    .collect(Collectors.toList());

            return Optional.of(infoOfFiles);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
