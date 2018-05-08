package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaInvalidStateException;
import io.github.eliux.mega.error.MegaInvalidTypeException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
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
    public void stage01_shouldUploadFileToRoot() {
        createTextFile("target/yolo-test.txt", "You only live once...");

        sessionMega.uploadFile("target/yolo-test.txt")
                .run();

        removeFile("target/yolo-test.txt");
    }

    @Test
    public void stage02_shouldUploadFileToTargetFolder() {
        createTextFile("target/yolo-infinite.txt", "You only live infinitive times...");

        sessionMega.uploadFile("target/yolo-infinite.txt", "megacmd4j/")
                .createRemoteIfNotPresent()
                .run();
    }

    @Test
    public void stage03_shouldUploadMultipleFilesAndCreateRemoteFolderSuccessfully() {
        createTextFiles(TEST_FILE_PREFIX, 10);

        final MegaCmdPutMultiple megaCmd = sessionMega.uploadFiles("megacmd4j/")
                .createRemoteIfNotPresent();

        IntStream.rangeClosed(1, 10).forEach(i -> {
            String filename = testTextFileName(TEST_FILE_PREFIX, i);
            megaCmd.addLocalFileToUpload(filename);
        });

        megaCmd.run();
    }

    @Test(expected = MegaException.class)
    public void stage04_given_multilevelfolder_when_mkdir_withoutRecursivelyFlag_then_fail() {
        sessionMega.makeDirectory("megacmd4j/level2/level3").run();
    }

    @Test
    public void stage05_given_multilevelfolder_when_mkdir_withRecursivelyAndIgnoreErrorIfExistsFlag_then_success() {
        sessionMega.makeDirectory("megacmd4j/level2/level3")
                .recursively()
                .ignoreErrorIfExists()
                .run();
    }

    @Test(expected = MegaInvalidStateException.class)
    public void stage06_given_multilevelfolder_when_mkdir_withRecursivelyAndthrowErrorIfExistsFlag_then_Fail() {
        sessionMega.makeDirectory("megacmd4j/level2/level3")
                .recursively()
                .throwErrorIfExists()
                .run();
    }

    @Test
    public void stage07_given_a_file_when_copy_then_success() {
        sessionMega.copy("megacmd4j/yolo.txt", "megacmd4j/level2/yolo.txt")
                .run();
    }

    @Test
    public void stage08_lsShouldReturnAFileAndADirectory() {
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

    @Test(expected = MegaInvalidTypeException.class)
    public void stage09_given_localPathDoesntExist_when_get_then_Fail() {
        sessionMega.get("megacmd4j/level2", "target/savedLevel2")
                .run();
    }

    @Test
    public void stage10_given_localPathWhichExist_when_get_then_success() {
        sessionMega.get("megacmd4j/level2", "target")
                .run();

        Assert.assertTrue(new File("target/level2/yolo.txt").isFile());
        Assert.assertTrue(new File("target/level2/level3").isDirectory());
    }

    @Test
    public void stage11_shouldDownloadASingleFileIntoLocalFolder() {
        sessionMega.get("megacmd4j/yolo-infinite.txt", "target/level2")
                .run();

        Assert.assertTrue(new File("target/level2/yolo-infinite.txt").exists());
    }

    @Test
    public void stage12_downloadedContentShouldBeConsistent() throws IOException {
        final String lineSeparator = System.getProperty("line.separator");

        final String firstlineOfGeneratedFile =
                Files.lines(new File("target/yolo-infinite.txt").toPath())
                        .collect(Collectors.joining(lineSeparator));
        final String firstLineOfDownloadedFile =
                Files.lines(new File("target/level2/yolo-infinite.txt").toPath())
                        .collect(Collectors.joining(lineSeparator));
        Assert.assertEquals(firstlineOfGeneratedFile, firstLineOfDownloadedFile);
    }

    @Test
    public void stage13_testFindRepeatedFileIfAllFilesAreMovedIntoSubpath() {
        sessionMega.move("megacmd4j/*.txt", "megacmd4j/level2/")
                .run();

        final List<FileInfo> currentFiles = sessionMega.ls("megacmd4j/").call();
        Assert.assertEquals(
                "Only 1 file was expected",
                1,
                currentFiles.size()
        );
        final FileInfo leftFile = currentFiles.get(0);
        Assert.assertTrue(
                "The unique file left was not a directory",
                leftFile.isDirectory()
        );
        Assert.assertEquals(
                "The expected directory was level2",
                "level2",
                leftFile.getName()
        );

        Assert.assertEquals(
                "It should only detect the latest version of yolo.txt",
                1L,
                sessionMega.ls("megacmd4j/level2/yolo.txt").count()
        );
    }

    @Test
    public void stage14_testOldVersionIsLeftIfRepeatedFileIsMovedBack(){
        sessionMega.move("megacmd4j/level2/yolo.txt", "megacmd4j/")
                .run();

        Assert.assertTrue(sessionMega.ls("megacmd4j/yolo.txt").exists());
        Assert.assertTrue(sessionMega.ls("megacmd4j/level2/yolo.txt").exists());
    }

    @After
    public void logout() {
        sessionMega.logout();
    }
}
