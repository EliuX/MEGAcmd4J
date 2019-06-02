package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Parse WhoAmI")
@Tag("whoami")
public class MegaCmdWhoAmITest {

  @DisplayName("When parse username then return email")
  @Test
  public void parseUsernameShouldReturnEmail() {
    //Given
    String validResponse = "Account e-mail: user@domain.com";

    //When
    final Optional<String> username = MegaCmdWhoAmI.parseUsername(validResponse);

    //Then
    assertTrue(username.isPresent());

    assertEquals(
        "user@domain.com",
        username.get()
    );
  }

  @DisplayName("When parse username of not logged in user then return nothing (Optional#empty)")
  @Test
  public void parseUsernameShouldFail() {
    //Given
    String invalidResponse = "[API:err: 21:14:32] Not logged in.";

    //When
    final Optional<String> username = MegaCmdWhoAmI.parseUsername(invalidResponse);

    //Then
    assertFalse(username.isPresent());
  }
}
