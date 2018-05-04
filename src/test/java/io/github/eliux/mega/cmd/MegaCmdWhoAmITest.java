package io.github.eliux.mega.cmd;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class MegaCmdWhoAmITest {

    @Test
    public void parseUsernameShouldReturnEmail() {
        //Given
        String validResponse = "Account e-mail: user@domain.com";

        //When
        final Optional<String> username = MegaCmdWhoAmI.parseUsername(validResponse);

        //Then
        Assert.assertTrue(username.isPresent());

        Assert.assertEquals(
                "user@domain.com",
                username.get()
        );
    }

    @Test
    public void parseUsernameShouldFail() {
        //Given
        String invalidResponse = "[API:err: 21:14:32] Not logged in.";

        //When
        final Optional<String> username = MegaCmdWhoAmI.parseUsername(invalidResponse);

        //Then
        Assert.assertFalse(username.isPresent());
    }
}
