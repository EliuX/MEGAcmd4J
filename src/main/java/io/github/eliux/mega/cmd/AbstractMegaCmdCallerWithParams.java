package io.github.eliux.mega.cmd;

public abstract class AbstractMegaCmdCallerWithParams<T>
        extends AbstractMegaCmdCaller<T> {

    public T call() {
        return executeSysCmd(String.format(
                "%s %s", getCmdAdaptedToPlatform(), cmdParams()
        ));
    }

    abstract String cmdParams();
}
