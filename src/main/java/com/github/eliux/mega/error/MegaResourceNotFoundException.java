package com.github.eliux.mega.error;

public class MegaResourceNotFoundException extends MegaException {

    public MegaResourceNotFoundException() {
        super("Resource not found");
    }
}
