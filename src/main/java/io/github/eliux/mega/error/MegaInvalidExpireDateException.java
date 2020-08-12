package io.github.eliux.mega.error;

public class MegaInvalidExpireDateException extends MegaException {

    public MegaInvalidExpireDateException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MegaInvalidExpireDateException(String errorMessage) {
        super("Date has wrong format: " + errorMessage);
    }
}
