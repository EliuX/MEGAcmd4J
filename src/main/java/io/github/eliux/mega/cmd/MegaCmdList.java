package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaIOException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MegaCmdList extends AbstractMegaCmdCallerWithParams<List<FileInfo>> {

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
    public List<FileInfo> call() {
        try {
            return MegaUtils.execWithOutput(executableCommand())
                    .stream().skip(1)
                    .map(FileInfo::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new MegaIOException("Error while listing " + remotePath);
        }
    }

    public long count() {
        try {
            return MegaUtils.execWithOutput(executableCommand())
                    .stream().skip(1)
                    .count();
        } catch (IOException e) {
            throw new MegaIOException("Error while listing " + remotePath);
        }
    }

    public boolean exists() {
        try {
            return count() > 0;
        } catch (MegaException e) {
            return false;
        }
    }
}
