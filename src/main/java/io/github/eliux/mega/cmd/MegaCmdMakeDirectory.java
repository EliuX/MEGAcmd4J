package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidStateException;

public class MegaCmdMakeDirectory extends AbstractMegaCmdProcedureWithParams {

    private final String remotePath;

    private boolean recursively;

    private boolean errorIgnoredIfExists;

    public MegaCmdMakeDirectory(String remotePath) {
        this.remotePath = remotePath;
    }

    @Override
    public String getCmd() {
        return "mkdir";
    }

    @Override
    String cmdParams() {
        StringBuilder sb = new StringBuilder();

        if (recursively)
            sb.append("-p ");

        sb.append(getRemotePath());

        return sb.toString();
    }

    public String getRemotePath() {
        return remotePath;
    }

    public MegaCmdMakeDirectory recursively() {
        recursively = true;
        return this;
    }

    public MegaCmdMakeDirectory notRecursively() {
        recursively = false;
        return this;
    }

    public boolean isRecursively() {
        return recursively;
    }

    public boolean isErrorIgnoredIfExists() {
        return errorIgnoredIfExists;
    }

    public MegaCmdMakeDirectory ignoreErrorIfExists() {
        errorIgnoredIfExists = true;
        return this;
    }

    public MegaCmdMakeDirectory throwErrorIfExists() {
        errorIgnoredIfExists = false;
        return this;
    }

    @Override
    public Void call() {
        try {
            return super.call();
        } catch (MegaInvalidStateException ex) {
            if (!errorIgnoredIfExists) {
                throw ex;
            }
        }

        return null;
    }
}
