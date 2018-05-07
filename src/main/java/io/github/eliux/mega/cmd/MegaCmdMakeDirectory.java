package io.github.eliux.mega.cmd;

public class MegaCmdMakeDirectory extends AbstractMegaCmdProcedureWithParams {

    private final String remotePath;

    private boolean recursively;

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
}
