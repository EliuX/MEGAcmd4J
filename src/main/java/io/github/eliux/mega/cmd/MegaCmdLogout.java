package io.github.eliux.mega.cmd;

import java.io.File;

public class MegaCmdLogout extends MegaProcedure {

    public MegaCmdLogout() {
        super();
    }

    @Override
    public Void call() {
        final Void result = super.call();
        removeAuthFiles();
        return result;
    }

    private void removeAuthFiles() {
        new File("megafind.txt").deleteOnExit();
        new File("localfind.txt").deleteOnExit();
    }

    @Override
    public String getCmd() {
        return getPlatform().cmdInstruction("mega-logout");
    }
}
