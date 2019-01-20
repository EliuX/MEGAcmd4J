package io.github.eliux.mega.platform;

/**
 * Encapsulate actions related to Unix based OS/Platforms
 */
public class UnixPlatform extends OSPlatform {

    @Override
    public String cmdInstruction(String cmd) {
        return String.format("mega-%s", cmd);
    }
}
