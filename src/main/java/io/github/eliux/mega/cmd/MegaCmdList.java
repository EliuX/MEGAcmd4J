package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaResourceNotFoundException;

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
            return MegaUtils.execCmdWithOutput(executableCommand()).stream().skip(1)
                    .filter(FileInfo::isValid)
                    .map(FileInfo::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new MegaIOException("Error while listing " + remotePath);
        }
    }

    public long count() throws MegaResourceNotFoundException{
        try {
            return MegaUtils.execCmdWithOutput(executableCommand()).stream()
                    .filter(FileInfo::isValid)
                    .count();
        } catch (IOException e) {
            throw new MegaIOException("Error while listing " + remotePath);
        }
    }

    public boolean exists() {
        try {
            MegaUtils.execCmdWithOutput(executableCommand());
            return true;
        } catch (MegaException e) {
            return false;
        }catch (IOException e) {
            throw new MegaIOException("Error while listing " + remotePath);
        }
    }
}
