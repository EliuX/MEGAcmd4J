package com.github.eliux.mega.platform;

public class UnixPlatform extends OSPlatform {
    @Override
    public String cmdInstruction(String cmd) {
        return String.format("mega-%s", cmd);
    }
}
