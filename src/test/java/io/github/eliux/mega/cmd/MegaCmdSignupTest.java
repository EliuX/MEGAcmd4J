package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.error.MegaWrongArgumentsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

@DisplayName("Sign up")
@Tag("cmd")
@Tag("signup")
public class MegaCmdSignupTest {

  @DisplayName("Sign up without using a name")
  @Test
  public void shouldSignUpWithoutUsingName() {
    Mega.signup("noreply@gmail.com", "anypassword").run();
  }

  @DisplayName("Sign up using a name")
  @Test
  public void shouldSignUpUsingName() {
    Mega.signup("noreply@gmail.com", "anypassword")
        .setName("Test User")
        .run();
  }

  @DisplayName("When sign up using an empty name then throw MegaWrongArgumentsException")
  @Test
  public void givenEmptyUserWhenSignupThenThrowWrongArgumentsException() {
    assertThrows(MegaWrongArgumentsException.class,
        () -> Mega.signup("", "anypassword").run());
  }

  @DisplayName("When sign up using an empty password then throw MegaWrongArgumentsException")
  @EnabledOnOs(WINDOWS)
  @Test
  public void givenEmptyPassword_whenSignupThenItShouldWork() {
    assertThrows(MegaWrongArgumentsException.class,
        () -> Mega.signup("noreply@gmail.com", "").run(),
        "It should not allow an empty password");
  }
}
