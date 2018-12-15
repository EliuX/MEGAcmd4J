package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaWrongArgumentsException;
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

    @Test(expected = MegaException.class)
    public void givenEmptyPassword_whenSignup_thenThrowWrongArgumentsException() {
        Mega.signup("noreply@gmail.com", "").run();
    }
}
