package com.github.eliux.mega.platform;

/**
 * Abstractions to encapsulate Operative System/Platforms specific actions.
 * E.g.: Running the MEGAcmd commands.
 */
public abstract class OSPlatform {

    private static OSPlatform current;

    public static OSPlatform getCurrent() {
        if (current == null) {
            if (isWindows()) {
                current = new WindowsPlatform();
            } else {
                current = new UnixPlatform();
            }
        }

        return current;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    abstract public String cmdInstruction(String cmd);
}
