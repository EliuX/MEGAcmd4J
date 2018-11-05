package io.github.eliux.mega.cmd;

public abstract class AbstractMegaCmdCallerWithParams<T>
        extends AbstractMegaCmdCaller<T> {

    @Override
    protected String executableCommand() {
        return String.format("%s %s", getCmdAdaptedToPlatform(), cmdParams());
    }

    abstract String cmdParams();
}
