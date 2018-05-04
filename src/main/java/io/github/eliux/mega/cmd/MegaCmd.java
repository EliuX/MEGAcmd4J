package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Optional;
import java.util.concurrent.Callable;

public abstract class MegaCmd<T> implements Callable<T> {

    public T call() {
        return executeSysCmd(getCmd())
                .orElseThrow(() -> new MegaInvalidResponseException(getCmd()));
    }

    protected OSPlatform getPlatform(){
        return OSPlatform.getCurrent();
    }

    public abstract String getCmd();

    protected abstract Optional<T> executeSysCmd(String cmdStr);
}
