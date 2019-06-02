package io.github.eliux.mega.cmd;

import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.error.MegaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MEGAcmd Common Commands")
public class MegaCmdCommonsTest extends AbstractRemoteTests {

  @DisplayName("Session should exist")
  @Test
  public void sessionShouldExist() {
    Assertions.assertNotNull(sessionMega.sessionID());
  }

  @DisplayName("Retrieve username (WhoAmI)")
  @Test
  public void shouldReturnUsername() {
    Assertions.assertNotNull(sessionMega.whoAmI());
  }

  @DisplayName("When change password to empty string then fail")
  @Test()
  public void emptyPasswordWhenChangePasswordThenFail() {
    final String currentPassword = System.getenv(Mega.PASSWORD_ENV_VAR);

    assertTimeout(ofMinutes(30), () ->
        assertThrows(MegaException.class,
            () -> sessionMega.changePassword(currentPassword, "  "))
    );
  }
}
