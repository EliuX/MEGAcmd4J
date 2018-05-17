package com.github.eliux.mega.platform;

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
        return System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
    }

    abstract public String cmdInstruction(String cmd);
}
