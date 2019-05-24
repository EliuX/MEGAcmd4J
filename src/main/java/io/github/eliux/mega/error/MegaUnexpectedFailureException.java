package io.github.eliux.mega.error;

public class MegaUnexpectedFailureException extends MegaException {

    public MegaUnexpectedFailureException(int code) {
        super("Unexpected failure with code %s", code);
    }

    public MegaUnexpectedFailureException() {
        super("Unexpected failure catched by MEGA");
    }
}
