package io.github.eliux.mega.cmd;

import static io.github.eliux.mega.MegaUtils.handleResult;

import io.github.eliux.mega.MegaUtils;

import io.github.eliux.mega.error.MegaIOException;
import java.io.IOException;


public abstract class AbstractMegaCmdRunner extends AbstractMegaCmd implements Runnable {

    @Override
    public void run() {
        executeSysCmd(getCmdAdaptedToPlatform());
    }

    protected void executeSysCmd(String cmdStr) {
        try {
          final int result = MegaUtils.execCmd(cmdStr);
          handleResult(result);
        } catch (IOException e) {
            throw new MegaIOException();
        } catch (InterruptedException e) {
            throw new MegaIOException(
                    "The execution of %s couldn't be finished", getCmd()
            );
        }
    }
}
