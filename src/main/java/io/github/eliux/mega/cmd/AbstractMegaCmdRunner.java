package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaIOException;

import java.io.IOException;
import java.util.Optional;

import static io.github.eliux.mega.MegaUtils.handleResult;

public abstract class AbstractMegaCmdRunner extends AbstractMegaCmd implements Runnable {

    @Override
    public void run() {
        executeSysCmd(getCmdAdaptedToPlatform());
    }

    protected void executeSysCmd(String cmdStr) {
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
    }
}
