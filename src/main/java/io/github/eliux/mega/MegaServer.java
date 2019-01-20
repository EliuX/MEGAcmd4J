package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaUnexpectedFailureException;
import io.github.eliux.mega.platform.OSPlatform;

public class MegaServer {

    private static MegaServer current;

    public static MegaServer getCurrent() {
        if (current == null) {
            current = new MegaServer();
        }

        return current;
    }

    private MegaServer() {
    }

    public void start() {
        try {
            MegaUtils.execCmd(OSPlatform.getCurrent().cmdInstruction("help"));
        } catch (Exception e) {
            throw new MegaUnexpectedFailureException();
        }
    }

    public void stop() {
        Mega.quit();
    }
}
