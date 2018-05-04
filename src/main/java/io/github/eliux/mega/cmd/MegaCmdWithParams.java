package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MegaCmdWithParams<T> extends MegaCmd<T> {

    @Override
    public T call() {
        return executeSysCmd(getCmd() + " " + cmdParams())
                .orElseThrow(() -> new MegaInvalidResponseException(getCmd()));
    }

    abstract String cmdParams();
}
