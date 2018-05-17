package com.github.eliux.mega.platform;

public class WindowsPlatform extends OSPlatform {
    @Override
    public String cmdInstruction(String cmd) {
        return String.format("cmd.exe /c MegaClient %s", cmd);
    }
}
