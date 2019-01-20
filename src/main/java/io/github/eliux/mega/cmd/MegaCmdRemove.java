package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaException;

import java.util.LinkedList;
import java.util.List;

public class MegaCmdRemove extends AbstractMegaCmdRunnerWithParams {

    private final String remotePath;

    private boolean recursivelyDeleted;

    private boolean errorIgnoredIfAbsent;

    public MegaCmdRemove(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    @Override
    List<String> cmdParams() {
        final List<String> cmdParams
                = new LinkedList<>();  //Never asks

        cmdParams.add("-f");

        if (recursivelyDeleted) {
            cmdParams.add("-r");
        }

        cmdParams.add(remotePath);

        return cmdParams;
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (MegaException ex) {
            if (!errorIgnoredIfAbsent) {
                throw ex;
            }
        }
    }

    @Override
    public String getCmd() {
        return "rm";
    }

    public MegaCmdRemove deleteRecursively() {
        recursivelyDeleted = true;
        return this;
    }

    public MegaCmdRemove deleteRecursivelyDisabled() {
        recursivelyDeleted = false;
        return this;
    }

    public boolean isRecursivelyDeleted() {
        return recursivelyDeleted;
    }

    public MegaCmdRemove ignoreErrorIfNotPresent() {
        errorIgnoredIfAbsent = true;
        return this;
    }

    public MegaCmdRemove reportErrorIfNotPresent() {
        errorIgnoredIfAbsent = false;
        return this;
    }

    public boolean isErrorIgnoredIfAbsent() {
        return errorIgnoredIfAbsent;
    }
}
