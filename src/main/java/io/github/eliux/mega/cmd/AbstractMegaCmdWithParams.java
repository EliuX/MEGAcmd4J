package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;

public abstract class MegaCmdWithParams<T> extends AbstractMegaCmd<T> {

    @Override
    public T call() {
        return executeSysCmd(getCmdAdaptedToPlatform() + " " + cmdParams())
                .orElseThrow(() -> new MegaInvalidResponseException(getCmd()));
    }

    abstract String cmdParams();
}
