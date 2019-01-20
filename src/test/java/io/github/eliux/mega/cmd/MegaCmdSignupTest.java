package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.error.MegaCmdInvalidArgumentException;
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

    @Test(expected = MegaCmdInvalidArgumentException.class)
    public void givenEmptyUser_whenSignup_thenThrowWrongArgumentsException() {
        Mega.signup("", "anypassword").run();
    }

    public void givenEmptyPassword_whenSignup_thenItShouldWork() {
        Mega.signup("noreply@gmail.com", "").run();
    }
}
