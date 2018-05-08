package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Optional;
import java.util.concurrent.Callable;

public abstract class AbstractMegaCmdCaller<T> extends AbstractMegaCmd implements Callable<T> {

    public T call() {
        return executeSysCmd(getCmdAdaptedToPlatform());
    }

    protected abstract T executeSysCmd(String cmdStr);
}
