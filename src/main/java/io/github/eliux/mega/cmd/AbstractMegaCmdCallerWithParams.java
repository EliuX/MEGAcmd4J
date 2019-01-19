package io.github.eliux.mega.cmd;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMegaCmdCallerWithParams<T>
        extends AbstractMegaCmdCaller<T> {

    @Override
    protected List<String> executableCommand() {
        List<String> command = new LinkedList<>();

        command.add(getCmdAdaptedToPlatform());

        command.addAll(cmdParams());

        return command;
    }

    abstract List<String> cmdParams();
}
