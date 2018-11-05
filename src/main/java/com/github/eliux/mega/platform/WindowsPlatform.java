package com.github.eliux.mega.platform;

/**
 * Class that encapsulates Windows specific actions.
 */
public class WindowsPlatform extends OSPlatform {

    @Override
    public String cmdInstruction(String cmd) {
        return String.format("cmd.exe /c MegaClient %s", cmd);
    }
}
