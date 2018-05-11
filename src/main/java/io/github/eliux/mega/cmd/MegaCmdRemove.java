package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaResourceNotFoundException;

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
    String cmdParams() {
        StringBuilder cmdParamsBuilder
                = new StringBuilder("-f ");  //Never asks

        if (recursivelyDeleted) {
            cmdParamsBuilder.append("-r ");
        }

        return cmdParamsBuilder.append(remotePath).toString();
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (MegaResourceNotFoundException ex) {
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
