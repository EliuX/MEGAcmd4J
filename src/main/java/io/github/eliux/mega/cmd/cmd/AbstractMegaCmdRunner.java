package io.github.eliux.mega.cmd.cmd;

<<<<<<< HEAD:src/main/java/com/github/eliux/mega/cmd/AbstractMegaCmdRunner.java
import io.github.eliux.mega.error.MegaIOException;
=======
import io.github.eliux.mega.MegaUtils;
>>>>>>> Add TTL to MEGAcmd commands:src/main/java/io/github/eliux/mega/cmd/AbstractMegaCmdRunner.java

import java.io.IOException;

import static com.github.eliux.mega.MegaUtils.handleResult;

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
