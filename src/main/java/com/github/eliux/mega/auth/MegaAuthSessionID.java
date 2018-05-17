package com.github.eliux.mega.auth;

import com.github.eliux.mega.Mega;
import com.github.eliux.mega.MegaSession;
import com.github.eliux.mega.error.MegaException;

import java.util.Optional;

public class MegaAuthSessionID extends MegaAuth {

    private final String sessionID;

    public MegaAuthSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return sessionID;
    }

    @Override
    public MegaSession login(){
        return new MegaSession(this);
    }

    public static final MegaAuthCredentials createFromEnvVariables(){
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
