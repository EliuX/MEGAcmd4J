package com.github.eliux.mega.cmd;

import com.github.eliux.mega.Mega;
import com.github.eliux.mega.error.MegaWrongArgumentsException;
import org.junit.Test;

public class MegaCmdSignupTest {

  @Test
  public void shouldSignUpWithoutUsingName() {
    Mega.signup("noreply@gmail.com", "anypassword").run();
  }

  @Test
  public void shouldSignUpUsingName() {
    Mega.signup("noreply@gmail.com", "anypassword")
        .setName("Test User")
        .run();
  }

  @Test(expected = MegaWrongArgumentsException.class)
  public void givenEmptyUser_whenSignup_thenThrowWrongArgumentsException() {
    Mega.signup("", "anypassword").run();
  }

  @Test(expected = MegaWrongArgumentsException.class)
  public void givenEmptyPassword_whenSignup_thenThrowWrongArgumentsException() {
    Mega.signup("noreply@gmail.com", "").run();
  }
}
