package io.github.eliux.mega.cmd;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMegaCmdRunnerWithParams
        extends AbstractMegaCmdRunner {

    @Override
    protected List<String> executableCommand() {
        List<String> cmdWithParams = new LinkedList<>();

        cmdWithParams.add(getCmdAdaptedToPlatform());

        cmdWithParams.addAll(cmdParams());

        return cmdWithParams;
    }

    abstract List<String> cmdParams();
}
