package com.github.eliux.mega;

import com.github.eliux.mega.auth.MegaAuth;
import com.github.eliux.mega.auth.MegaAuthCredentials;
import com.github.eliux.mega.auth.MegaAuthSessionID;
import com.github.eliux.mega.cmd.MegaCmdSession;
import com.github.eliux.mega.cmd.MegaCmdSignup;
import com.github.eliux.mega.error.MegaException;

/**
 * Entry point of the MEGAcmd4J library: It allows you to execute the functions you may use without
 * have a session started. <p> Use {@link #init()} to start a session, or any other function return
 * to you a {@link MegaSession} instance
 *
 * @see {@link MegaSession}
 */
public interface Mega {

  String USERNAME_ENV_VAR = "MEGA_EMAIL";
  String PASSWORD_ENV_VAR = "MEGA_PWD";

  static String[] envVars() {
    String pathVar = "PATH=" + System.getenv("PATH");
    return new String[]{pathVar};
  }

  /**
   * Retrieves the existing session or starts a new one
   *
   * @return {@link MegaSession} not null
   */
  static MegaSession init() {
    try {
      return currentSession();
    } catch (MegaException e) {
      return login(MegaAuthCredentials.createFromEnvVariables());
    }
  }

  /**
   * Login into Mega
   *
   * @param credentials {@link MegaAuth} with authentication mechanism
   * @return {@link MegaSession} not null
   */
  static MegaSession login(MegaAuth credentials) {
    return credentials.login();
  }

  /**
   * Retrieves the current Mega session
   *
   * @return {@link MegaSession} not null
   */
  static MegaSession currentSession() {
    final String sessionID = new MegaCmdSession().call();
    return login(new MegaAuthSessionID(sessionID));
  }

  /**
   * Register as user with a given email
   *
   * @param username {@link String} with the username (email)
   * @param password {@link String} with the password
   * @return {@link MegaCmdSignup} not null
   */
  static MegaCmdSignup signup(String username, String password) {
    return new MegaCmdSignup(username, password);
  }
}
