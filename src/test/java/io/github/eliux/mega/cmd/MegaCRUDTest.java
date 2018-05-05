package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.MegaTestUtils;
import io.github.eliux.mega.MegaUtilsTest;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.github.eliux.mega.MegaTestUtils.createTextFiles;
import static io.github.eliux.mega.MegaTestUtils.removeTextFiles;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MegaCRUDTest {

    private static final String TEST_FILE_PREFIX = "yolo";

    MegaSession sessionMega;

    @Before
    public void setup() {
        sessionMega = Mega.init();
    }

    @Test
    public void shouldUploadMultipleFilesOkAndCreateRemoteFolder() {
        createTextFiles(TEST_FILE_PREFIX, 10);

        sessionMega.uploadFiles("megacmd4j/", "target/yolo*.txt")
                .createRemoteIfNotPresent()
                .call();
    }

    @After
    public void logout() {
        sessionMega.logout();
    }
}
