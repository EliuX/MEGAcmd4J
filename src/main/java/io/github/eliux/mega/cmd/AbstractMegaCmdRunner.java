package io.github.eliux.mega.cmd;


import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;

import java.io.IOException;


public abstract class AbstractMegaCmdRunner extends AbstractMegaCmd implements Runnable {

    @Override
    public void run() {
        executeSysCmd(executableCommandArray());
    }

    protected void executeSysCmd(String... cmdStr) {
        try {
          final int result = MegaUtils.execCmd(cmdStr);
          MegaUtils.handleResult(result);
        } catch (IOException e) {
            throw new MegaIOException();
        } catch (InterruptedException e) {
            throw new MegaIOException(
                    "The execution of %s couldn't be finished", getCmd()
            );
        }
    }
}
