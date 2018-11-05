package io.github.eliux.mega.error;

public class MegaLoginException extends MegaException {

    public MegaLoginException(String errorMessage) {
        super(errorMessage);
    }

    public MegaLoginException(String errorMessage, Throwable detailedError) {
        super(errorMessage, detailedError);
    }
}
