package io.github.eliux.mega.error;

public class MegaIOException extends MegaException {

    public MegaIOException(String message) {
        super(message);
    }

    public MegaIOException(String message, String... args) {
        this(String.format(message, args));
    }

    public MegaIOException() {
        this("Error while connecting to Mega services");
    }
}
