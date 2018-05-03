package io.github.eliux.mega.cmd;

import java.util.Optional;

public class MegaCmdPut extends MegaCmd {

    public MegaCmdPut() {
        super();
    }

    public String getCmd() {
        return "mega-put";
    }

    @Override
    protected Optional executeSysCmd(String cmdStr) {
        return Optional.empty();
    }
}
