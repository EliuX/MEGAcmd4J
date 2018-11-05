package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaUnexpectedFailureException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MegaCmdCommonsTest {

  MegaSession sessionMega;

  @Before
  public void setup() {
    sessionMega = Mega.init();
  }

  @Test
  public void sessionShouldExist() {
    Assert.assertNotNull(sessionMega.sessionID());
  }

  @Test
  public void shouldReturnUsername() {
    Assert.assertNotNull(sessionMega.whoAmI());
  }

  @Test(expected = MegaException.class, timeout = 30000)
  public void given_emptyPassword_when_changePassword_then_fail() {
    final String currentPassword = System.getenv(Mega.PASSWORD_ENV_VAR);

    sessionMega.changePassword(currentPassword, "  ");
  }

  @After
  public void logout() {
    sessionMega.logout();
  }
}
