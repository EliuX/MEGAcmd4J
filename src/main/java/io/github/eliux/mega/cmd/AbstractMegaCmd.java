package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMegaCmd<T> {

    protected List<String> executableCommand() {
        final String[] cmdInstructions = MegaUtils.convertInstructionsToExecParams(
            getCmdAdaptedToPlatform()
        );
        return Arrays.asList(cmdInstructions);
    }

    protected String[] executableCommandArray(){
        final List<String> execCmd = executableCommand();

        String[] execCmdArray = new String[execCmd.size()];
        execCmd.toArray(execCmdArray);

        return execCmdArray;
    }

    protected String getCmdAdaptedToPlatform() {
        return OSPlatform.getCurrent().cmdInstruction(getCmd());
    }

    public abstract String getCmd();
}
