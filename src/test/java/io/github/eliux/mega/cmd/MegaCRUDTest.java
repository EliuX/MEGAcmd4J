package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaException;
import org.junit.*;

import java.util.Optional;

import static io.github.eliux.mega.MegaTestUtils.createTextFiles;
import static io.github.eliux.mega.MegaTestUtils.removeTextFiles;


public class MegaCRUDTest {

    private static final String TEST_FILE_PREFIX = "yolo";

    MegaSession sessionMega;

    @Before
    public void setup() {
        createTextFiles(TEST_FILE_PREFIX, 10);

        sessionMega = Mega.init();
    }

    @Test
    public void sessionShouldExist() {

    }

    @After
    public void logout() {
        sessionMega.logout();

        removeTextFiles(TEST_FILE_PREFIX, 10);
    }
}
