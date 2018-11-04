package io.github.eliux.mega.cmd.cmd;

public abstract class AbstractMegaCmdRunnerWithParams
        extends AbstractMegaCmdRunner {

    @Override
    public void run() {
        executeSysCmd(getCmdAdaptedToPlatform() + " " + cmdParams());
    }

    abstract String cmdParams();
}
