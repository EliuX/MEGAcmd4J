package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Optional;
import java.util.concurrent.Callable;

public abstract class AbstractMegaCmd<T> implements Callable<T> {

    public T call() {
        return executeSysCmd(getCmdAdaptedToPlatform())
                .orElseThrow(() -> new MegaInvalidResponseException(getCmd()));
    }

    protected String getCmdAdaptedToPlatform() {
        return OSPlatform.getCurrent().cmdInstruction(getCmd());
    }

    public abstract String getCmd();

    protected abstract Optional<T> executeSysCmd(String cmdStr);
}
