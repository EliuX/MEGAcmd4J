package io.github.eliux.mega.cmd.cmd;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class MegaCmdSessionTest {

  @Test
  public void parseSessionIDShouldBeOk() {
    //Given
    String validResponse = "Your (secret) session is: Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX";

    //When
    final Optional<String> id = MegaCmdSession.parseSessionID(validResponse);

    //Then
    Assert.assertTrue(id.isPresent());

    Assert.assertEquals(
        "Ae9r6XXXqUZGhXEIUoy7C85XhPq9vOAr2Sc94axXXXX-T3JZZE9kOEt3dDjWGMscV2il65Zo-mFMEXXX",
        id.get()
    );
  }

  @Test
  public void parseSessionIDShouldFail() {
    //Given
    String invalidResponse = "[API:err: 21:14:32] Not logged in.";

<<<<<<< HEAD:src/test/java/com/github/eliux/mega/cmd/MegaCmdSessionTest.java
        //When
        final Optional<String> noId = MegaCmdSession.parseSessionID(invalidResponse);
=======
    //When
    final Optional<String> noId = MegaCmdSession.parseSessionID(invalidResponse);
>>>>>>> Use ubuntu trusty for Travis CI:src/test/java/io/github/eliux/mega/cmd/MegaCmdSessionTest.java

    //Then
    Assert.assertFalse(noId.isPresent());
  }
}
