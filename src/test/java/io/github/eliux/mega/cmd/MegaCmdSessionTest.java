package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Sessions")
@Tag("session")
public class MegaCmdSessionTest {

  @DisplayName("When parse valid session id then return expected value")
  @Test
  public void parseSessionIDShouldBeOk() {
    //Given
    String validResponse = "Your (secret) session is: Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX";

    //When
    final Optional<String> id = MegaCmdSession.parseSessionID(validResponse);

    //Then
    assertTrue(id.isPresent(), "No id was found");

    assertEquals(
        "Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX",
        id.get(),
        "The found id has not the expected value"
    );
  }

  @DisplayName("When parse session id for a not logged in user then return nothing (Optional#empty)")
  @Test
  public void parseSessionIDShouldFail() {
    //Given
    String invalidResponse = "[API:err: 21:14:32] Not logged in.";

    //When
    final Optional<String> noId = MegaCmdSession.parseSessionID(invalidResponse);

    //Then
    assertFalse(noId.isPresent(), "Unexpected session id");
  }
}
