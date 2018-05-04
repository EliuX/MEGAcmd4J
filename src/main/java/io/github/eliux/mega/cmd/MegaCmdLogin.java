package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.eliux.mega.MegaUtils.handleResult;

public class MegaCmdLogin extends AbstractMegaCmdWithParams<Boolean> {

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
    protected Optional<Boolean> executeSysCmd(String cmdStr) {
        try {
            final int result = MegaUtils.execCmd(cmdStr);
            handleResult(result);
            return Optional.of(Boolean.TRUE);
        } catch (IOException | InterruptedException e) {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Override
    protected String cmdParams() {
        return cmdParams;
    }
}
