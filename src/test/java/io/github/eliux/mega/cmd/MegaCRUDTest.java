package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaInvalidStateException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.stream.IntStream;

import static io.github.eliux.mega.MegaTestUtils.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MegaCRUDTest {

    private static final String TEST_FILE_PREFIX = "yolo";

    MegaSession sessionMega;

    @Before
    public void setup() {
        sessionMega = Mega.init();
    }

    @Test
    public void shouldUploadFileToRoot() {
        createTextFile("target/yolo-test.txt", "You only live once...");

        sessionMega.uploadFile("target/yolo-test.txt")
                .call();

        removeFile("target/yolo-test.txt");
    }

    @Test
    public void shouldUploadFileToTargetFolder() {
        createTextFile("target/yolo-infinite.txt", "You only live infinitive times...");

        sessionMega.uploadFile("target/yolo-infinite.txt", "megacmd4j/")
                .createRemoteIfNotPresent()
                .call();

        removeFile("target/yolo-infinite.txt");
    }

    @Test
    public void shouldUploadMultipleFilesOkAndCreateRemoteFolder() {
        createTextFiles(TEST_FILE_PREFIX, 10);

        final MegaCmdPutMultiple megaCmd = sessionMega.uploadFiles("megacmd4j/")
                .createRemoteIfNotPresent();

        IntStream.rangeClosed(1, 10).forEach(i -> {
            String filename = testTextFileName(TEST_FILE_PREFIX, i);
            megaCmd.addLocalFileToUpload(filename);
        });

        megaCmd.call();

        removeTextFiles(TEST_FILE_PREFIX, 10);
    }

    @Test(expected = MegaException.class)
    public void given_multilevelfolder_when_mkdir_withoutRecursivelyFlag_then_fail() {
        sessionMega.makeDirectory("megacmd4j/level2/level3").call();
    }

    @Test
    public void given_multilevelfolder_when_mkdir_withRecursivelyAndIgnoreErrorIfExistsFlag_then_Success() {
        sessionMega.makeDirectory("megacmd4j/level2/level3")
                .recursively()
                .ignoreErrorIfExists()
                .call();
    }

    @Test(expected = MegaInvalidStateException.class)
    public void given_multilevelfolder_when_mkdir_withRecursivelyAndthrowErrorIfExistsFlag_then_Fail() {
        sessionMega.makeDirectory("megacmd4j/level2/level3")
                .recursively()
                .throwErrorIfExists()
                .call();
    }

    @Test
    public void given_a_file_when_copy_then_success(){
        sessionMega.copy("megacmd4j/yolo.txt", "megacmd4j/level2/yolo.txt")
                .call();
    }

    @Test
    public void lsShouldReturnAFileAndADirectory(){
        final List<FileInfo> files = sessionMega.ls("megacmd4j/level2").call();

        Assert.assertEquals(
                "There should be 2 elements",
                2,
                files.size()
        );

        final FileInfo fileInfo = files.stream().filter(x -> x.getName().equals("yolo.txt"))
                .findAny().orElseThrow(() -> new MegaInvalidStateException(
                        "The previously copied filed was not found"
                ));

        Assert.assertTrue(fileInfo.isFile());

        final FileInfo directoryInfo = files.stream().filter(x -> x.getName().equals("level3"))
                .findAny().orElseThrow(() -> new MegaInvalidStateException(
                        "The previously copied filed was not found"
                ));

        Assert.assertTrue(directoryInfo.isDirectory());
    }

    @After
    public void logout() {
        sessionMega.logout();
    }
}
