package io.github.eliux.mega.error;

public class MegaException extends RuntimeException {

    public MegaException(String errorMessage) {
        super(errorMessage);
    }

    public MegaException(String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
    }

    public static final MegaException nonExistingEnvVariable(String envVarName) {
        return new MegaException(
                "You must define the variable %s in your environment",
                envVarName
        );
    }
}
