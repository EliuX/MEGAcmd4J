package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

import java.io.IOException;
import java.util.Optional;

import static io.github.eliux.mega.MegaUtils.handleResult;

public class MegaCmdLogin extends MegaCmdWithParams<Boolean> {

    public MegaCmdLogin(String... params) {
        super(params);
    }

    @Override
    public String getCmd() {
        return "mega-login.bat";
    }

    @Override
    protected Optional<Boolean> executeSysCmd(String cmdStr) {
        try {
            final int result = MegaUtils.execCmd(cmdStr);
            handleResult(result);
            return Optional.of(Boolean.TRUE);
        } catch (IOException | InterruptedException e) {
            return Optional.of(Boolean.FALSE);
        }
    }
}
