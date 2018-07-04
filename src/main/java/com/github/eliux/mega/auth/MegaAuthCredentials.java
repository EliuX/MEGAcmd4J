package com.github.eliux.mega.auth;

import com.github.eliux.mega.Mega;
import com.github.eliux.mega.MegaSession;
import com.github.eliux.mega.cmd.MegaCmdLogin;
import com.github.eliux.mega.error.MegaException;
import java.util.Optional;

/**
 * Logs into MEGA with an email/username and password combination
 */
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
    megaCmdLogin.run();

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
