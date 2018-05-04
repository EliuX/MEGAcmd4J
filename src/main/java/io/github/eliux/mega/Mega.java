package io.github.eliux.mega;

import io.github.eliux.mega.auth.MegaAuth;
import io.github.eliux.mega.auth.MegaAuthCredentials;
import io.github.eliux.mega.auth.MegaAuthSessionID;
import io.github.eliux.mega.cmd.MegaCmdSession;
import io.github.eliux.mega.error.MegaException;

public interface Mega {

    String USERNAME_ENV_VAR = "MEGA_EMAIL";
    String PASSWORD_ENV_VAR = "MEGA_PWD";

    static String[] envVars() {
        String pathVar = "PATH=" + System.getenv("PATH");
        return new String[]{pathVar};
    }

    static MegaSession init() {
        try {
            return currentSession();
        } catch (MegaException e) {
            return login(MegaAuthCredentials.createFromEnvVariables());
        }
    }

    static MegaSession login(MegaAuth credentials) {
        return credentials.login();
    }

    static MegaSession currentSession() {
        final String sessionID = new MegaCmdSession().call();
        return login(new MegaAuthSessionID(sessionID));
    }
}
