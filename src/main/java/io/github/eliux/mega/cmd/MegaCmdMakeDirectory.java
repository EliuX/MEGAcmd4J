package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaWrongArgumentsException;

import java.util.LinkedList;
import java.util.List;

public class MegaCmdMakeDirectory extends AbstractMegaCmdRunnerWithParams {

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
    List<String> cmdParams() {
        List<String> params = new LinkedList<>();

        if (recursively) {
            params.add("-p");
        }

        params.add(getRemotePath());

        return params;
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
    public void run() {
        try {
            super.run();
        } catch (MegaWrongArgumentsException ex) {
            if (!errorIgnoredIfExists) {
                throw ex;
            }
        }
    }
}
