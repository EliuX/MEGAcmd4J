package io.github.eliux.mega.auth;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.cmd.MegaCmdLogin;
import io.github.eliux.mega.error.MegaException;

import java.util.Optional;

public class MegaAuthCredentials extends MegaAuth {

    private final String username;

    private final String password;

    public MegaAuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public MegaSession login() {
        final MegaCmdLogin megaCmdLogin = new MegaCmdLogin(username, password);
        megaCmdLogin.call();

        return new MegaSession(this);
    }

    public static final MegaAuthCredentials createFromEnvVariables() {
        String username = Optional.ofNullable(System.getenv(Mega.USERNAME_ENV_VAR))
                .orElseThrow(() -> MegaException.nonExistingEnvVariable(
                        Mega.USERNAME_ENV_VAR
                ));

        String password = Optional.ofNullable(System.getenv(Mega.PASSWORD_ENV_VAR))
                .orElseThrow(() -> MegaException.nonExistingEnvVariable(
                        Mega.PASSWORD_ENV_VAR
                ));

        return new MegaAuthCredentials(username, password);
    }
}
