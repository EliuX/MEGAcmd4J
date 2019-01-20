package io.github.eliux.mega.error;

public class MegaCmdInvalidArgumentException extends MegaException {

    public MegaCmdInvalidArgumentException() {
        super("Invalid arguments");
    }

    public MegaCmdInvalidArgumentException(String errorMessage) {
        super(errorMessage);
    }

    public MegaCmdInvalidArgumentException(String errorMessage, Object... args) {
        super(errorMessage, args);
    }
}
