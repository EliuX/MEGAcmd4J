package io.github.eliux.mega.error;

public class MegaInvalidStateException extends MegaException {

    public MegaInvalidStateException() {
        super("Invalid state");
    }

    public MegaInvalidStateException(String message){
        super(message);
    }
}
