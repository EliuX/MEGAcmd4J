package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.auth.MegaAuthCredentials;
import io.github.eliux.mega.error.MegaException;
import org.junit.*;


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

    public void usernameShouldBeGiven() {
        //When
        sessionMega.whoAmI();
    }

    @After
    public void logout() {
        sessionMega.logout();
    }
}
