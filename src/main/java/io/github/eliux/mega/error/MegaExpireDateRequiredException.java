package io.github.eliux.mega.error;

public class MegaExpireDateRequiredException extends MegaException {

    public MegaExpireDateRequiredException() {
        super("You must define an expiry date");
    }
}
