package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaIOException;

import java.io.IOException;
import java.util.Optional;

import static io.github.eliux.mega.MegaUtils.handleResult;

public abstract class MegaProcedure extends MegaCmd<Void> {

    public MegaProcedure() {
        super();
    }

    @Override
    public Void call() {
        executeSysCmd(getCmd());
        return null;
    }

    @Override
    protected Optional<Void> executeSysCmd(String cmdStr) {
        try {
            final Process exec = Runtime.getRuntime().exec(cmdStr);
            handleResult(exec.waitFor());
        } catch (IOException e) {
            throw new MegaIOException();
        } catch (InterruptedException e) {
            throw new MegaIOException(
                    "The execution of %s couldn't be finished", getCmd()
            );
        }

        return Optional.empty();
    }
}
