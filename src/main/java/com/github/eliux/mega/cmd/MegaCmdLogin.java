package com.github.eliux.mega.cmd;

import com.github.eliux.mega.MegaUtils;
import com.github.eliux.mega.error.MegaLoginException;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MegaCmdLogin extends AbstractMegaCmdRunnerWithParams {

    private final String cmdParams;

    public MegaCmdLogin(String... cmdParams) {
        super();
        this.cmdParams = Arrays.asList(cmdParams).stream()
                .collect(Collectors.joining(" "));
    }

    @Override
    public String getCmd() {
        return "login";
    }

    @Override
    protected void executeSysCmd(String cmdStr) {
        try {
            final int result = MegaUtils.execCmd(cmdStr);
            MegaUtils.handleResult(result);
        } catch (IOException e) {
            throw new MegaLoginException(
                    "Error while executing login command in Mega"
            );
        } catch (InterruptedException e) {
            throw new MegaLoginException("The login was interrupted");
        }
    }

    @Override
    protected String cmdParams() {
        return cmdParams;
    }
}
