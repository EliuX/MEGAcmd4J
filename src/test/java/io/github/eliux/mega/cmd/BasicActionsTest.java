package io.github.eliux.mega.cmd;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaTestUtils;
import io.github.eliux.mega.error.MegaException;
import io.github.eliux.mega.error.MegaInvalidStateException;
import io.github.eliux.mega.error.MegaInvalidTypeException;
import io.github.eliux.mega.error.MegaResourceNotFoundException;
import io.github.eliux.mega.error.MegaWrongArgumentsException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("MEGAcmd Basic Commands")
@Tag("cmd")
@Tag("integration")
@TestMethodOrder(OrderAnnotation.class)
public class BasicActionsTest extends AbstractRemoteTests {

  @AfterAll
  static void finishSession() {
    sessionMega.removeDirectory("megacmd4j").run();
    sessionMega.remove("yolo*.txt").ignoreErrorIfNotPresent().run();
  }

  @DisplayName("Enable HTTPS")
  @Tag("https")
  @Order(0)
  @Test()
  public void shouldEnableHttps() {
    assertTrue(
        sessionMega.enableHttps(),
        "HTTPS should be enabled after the action"
    );
  }

  @DisplayName("Delete directory if exists")
  @Tag("rm")
  @Order(0)
  @Test()
  public void shouldDeleteWorkingDirectoryIfExist() {
    sessionMega.remove("megacmd4j")
        .deleteRecursively()
        .ignoreErrorIfNotPresent()
        .run();
  }

  @DisplayName("Session should have authentication object")
  @Tag("auth")
  @Order(0)
  @Test()
  public void sessionShouldHaveAuthenticationObject() {
    assertNotNull(sessionMega.getAuthentication());
  }

  @DisplayName("HTTPS should be enabled")
  @Tag("https")
  @Order(1)
  @Test()
  public void httpsShouldBeEnabled() {
    assertTrue(sessionMega.isHttpsEnabled(), "HTTPS should be enabled");
  }

  @DisplayName("Upload file to root folder")
  @Tag("put")
  @Order(1)
  @Test()
  public void shouldUploadFileToRoot() {
    MegaTestUtils.createTextFile(
        "target/yolo-test.txt",
        "You only live once..."
    );

    sessionMega.uploadFile("target/yolo-test.txt")
        .run();

    MegaTestUtils.removeFile("target/yolo-test.txt");
  }

  @DisplayName("Upload file to target folder")
  @Tag("put")
  @Order(2)
  @Test()
  public void shouldUploadFileToTargetFolder() {
    MegaTestUtils.createTextFile(
        "target/yolo-infinite.txt",
        "You only live infinitive times..."
    );

    sessionMega.uploadFile("target/yolo-infinite.txt", "/megacmd4j/")
        .createRemotePathIfNotPresent()
        .run();
  }

  @DisplayName("Upload multiple files and create remote folder")
  @Tag("put")
  @Order(3)
  @Test()
  public void shouldUploadMultipleFilesAndCreateRemoteFolderSuccessfully() {
    MegaTestUtils.createTextFiles("yolo", 10);

    final MegaCmdPutMultiple megaCmd = sessionMega.uploadFiles("/megacmd4j/")
        .createRemotePathIfNotPresent();

    IntStream.rangeClosed(1, 10).forEach(i -> {
      String filename = MegaTestUtils.testTextFileName("yolo", i);
      megaCmd.addLocalFileToUpload(filename);
    });

    megaCmd.run();
  }

  @DisplayName("When create folder with multilevel structure without recursively flag then fail")
  @Tag("mkdir")
  @Order(4)
  @Test()
  public void failWhenCreateMultiLevelDirectoryWithoutRecursivelyFlag() {
    assertThrows(MegaWrongArgumentsException.class,
        () -> sessionMega.makeDirectory("megacmd4j/level2/level3").run());
  }

  @DisplayName("When create folder with multilevel structure with recursively flag then success")
  @Tag("mkdir")
  @Order(5)
  @Test()
  public void succeedWhenCreateMultiLevelDirectoryWithRecursivelyFlag() {
    sessionMega.makeDirectory("megacmd4j/level2/level3")
        .recursively()
        .ignoreErrorIfExists()
        .run();
  }

  @DisplayName("When try to create existing folder with multilevel structure then fail")
  @Tag("mkdir")
  @Order(6)
  @Test()
  public void failWhenCreateExistingMultiLevelDirectory() {
    assertThrows(MegaInvalidStateException.class,
        () -> sessionMega.makeDirectory("megacmd4j/level2/level3")
            .recursively()
            .throwErrorIfExists()
            .run());
  }

  @DisplayName("Upload file remotely")
  @Tag("cp")
  @Order(7)
  @Test()
  public void succeedCopyFileRemotely() {
    sessionMega.copy("megacmd4j/yolo.txt", "megacmd4j/level2/yolo.txt")
        .run();
  }

  @DisplayName("Create folder with spaces in the name")
  @Tag("mkdir")
  @Order(7)
  @Test()
  public void shouldCreateFolderWithSpace() {
    sessionMega.makeDirectory("megacmd4j/level2/level 3 with spaces")
        .recursively()
        .run();

  }

  @DisplayName("Should return previously uploaded file")
  @Tag("ls")
  @Order(8)
  @Test()
  public void lsShouldReturnAFileAndADirectory() {
    final List<FileInfo> files = sessionMega.ls("megacmd4j/level2").call();

    assertEquals(
        3,
        files.size(),
        "There should be 3 elements"
    );

    final FileInfo fileInfo = files.stream()
        .filter(x -> x.getName().equals("yolo.txt"))
        .findAny().orElseThrow(() -> new MegaInvalidStateException(
            "The previously uploaded filed was not found"
        ));

    assertTrue(fileInfo.isFile(), "The found object is not a file");

    final FileInfo directoryInfo = files.stream()
        .filter(x -> x.getName().equals("level3"))
        .findAny().orElseThrow(() -> new MegaInvalidStateException(
            "The previously uploaded folder was not found"
        ));

    assertTrue(
        directoryInfo.isDirectory(),
        "The found object is not a directory"
    );

    final FileInfo directoryWithSpacesInfo = files.stream()
        .filter(x -> x.getName().equals("level 3 with spaces"))
        .findAny().orElseThrow(() -> new MegaInvalidStateException(
            "The previously uploaded folder with spaces was not found"
        ));

    assertTrue(
        directoryWithSpacesInfo.isDirectory(),
        "The found object is not a directory"
    );
  }

  @DisplayName("When download remote folder to nonexistent local folder then fail")
  @Tag("get")
  @Order(9)
  @Test()
  public void failWhenDownloadRemoteFolderToNonExistentLocalFolder() {
    assertThrows(MegaInvalidTypeException.class,
        () -> sessionMega.get("megacmd4j/level2", "target/savedLevel2")
            .run());
  }

  @DisplayName("When download remote folder to existing local folder then success")
  @Tag("get")
  @Order(10)
  @Test()
  public void succeedWhenDownloadRemoteFolderToExistingLocalFolder() {
    sessionMega.get("megacmd4j/level2", "target")
        .run();

    assertTrue(
        new File("target/level2/yolo.txt").isFile(),
        "Invalid downloaded file"
    );
    assertTrue(
        new File("target/level2/level3").isDirectory(),
        "Invalid downloaded directory"
    );
    assertTrue(
        new File("target/level2/level 3 with spaces").isDirectory(),
        "Invalid downloaded directory"
    );
  }

  @DisplayName("Download single remote file to local folder")
  @Tag("get")
  @Order(11)
  @Test()
  public void shouldDownloadASingleFileIntoLocalFolder() {
    sessionMega.get("megacmd4j/yolo-infinite.txt", "target/level2")
        .run();

    assertTrue(
        new File("target/level2/yolo-infinite.txt").exists(),
        "The downloaded file should exist locally"
    );
  }

  @DisplayName("Downloaded file should have the same content as when it was uploaded")
  @Tag("get")
  @Order(12)
  @Test()
  public void downloadedContentShouldBeConsistent() throws IOException {
    final String lineSeparator = System.getProperty("line.separator");

    final String firstLineOfGeneratedFile =
        Files.lines(new File("target/yolo-infinite.txt").toPath())
            .collect(Collectors.joining(lineSeparator));

    final String firstLineOfDownloadedFile =
        Files.lines(new File("target/level2/yolo-infinite.txt").toPath())
            .collect(Collectors.joining(lineSeparator));

    assertEquals(firstLineOfGeneratedFile, firstLineOfDownloadedFile,
        "The downloaded file has differed its content");
  }

  @DisplayName("Move multiple files using pattern into sub-path: *")
  @Tag("ls")
  @Order(13)
  @Test()
  public void moveMultipleFilesUsingPatternIntoSubpath() {
    sessionMega.move("megacmd4j/*-*.txt", "/megacmd4j/level2/")
        .run();

    final List<FileInfo> currentFiles = sessionMega.ls("megacmd4j/").call();
    assertEquals(3, currentFiles.size(),
        "Only 2 files were expected");

    final long amountOfDirectoriesLeft = currentFiles.stream()
        .filter(FileInfo::isDirectory).count();
    assertEquals(2, amountOfDirectoriesLeft,
        "There should be only 1 directory left");

    final long amountOfFilesLeft = currentFiles.stream()
        .filter(FileInfo::isFile)
        .count();
    assertEquals(1, amountOfFilesLeft,
        "There should be only 1 file left");
  }

  @DisplayName("Upload file to remote folder with whitespace")
  @Tag("put")
  @Order(13)
  @Test()
  public void uploadFileToFoldersWithWhitespace() {
    MegaTestUtils.createTextFile(
        "target/folder with white spaces/yolo.txt",
        "You only live infinitive times..."
    );

    sessionMega.uploadFile("target/folder with white spaces/yolo.txt",
        "/megacmd4j/remote folder/")
        .createRemotePathIfNotPresent()
        .run();

    assertTrue(sessionMega.ls("/megacmd4j/remote folder/yolo.txt")
        .exists());
  }


  @DisplayName("Create remote folder with whitespaces")
  @Tag("put")
  @Order(14)
  @Test()
  public void shouldCreateFolderWithWhitespaces() {
    sessionMega.makeDirectory("megacmd4j/level2/another folder/")
        .recursively()
        .run();

    assertTrue(sessionMega.ls("megacmd4j/level2/another folder/")
        .exists());
  }

  @DisplayName("HTTPS should be disabled")
  @Tag("https")
  @Order(14)
  @Test()
  public void shouldDisableHTTPS() {
    assertFalse(
        sessionMega.disableHttps(),
        "HTTPS should be disabled"
    );
  }

  @DisplayName("When list an existing empty remote folder then exits should return true")
  @Tag("ls")
  @Order(14)
  @Test()
  public void givenEmptyFolderWhenListThenExistsIsTrue() {
    assertTrue(
        sessionMega.ls("megacmd4j/level2/level3").exists(),
        "The directory level3 should exist"
    );
  }

  @DisplayName("When list a non-existing empty remote folder then exits should return false")
  @Tag("ls")
  @Order(15)
  @Test()
  public void givenNonExistingFileWhenListThenExistsIsFalse() {
    assertFalse(
        sessionMega.ls("megacmd4j/level2/level33").exists(),
        "That file/directory doesnt exist"
    );
  }

  @DisplayName("Move file from/to remote path with whitespaces")
  @Tag("ls")
  @Tag("mv")
  @Order(15)
  @Test()
  public void shouldMoveFileFromPathWithWhitespace() {
    sessionMega.move("megacmd4j/remote folder/yolo.txt",
        "megacmd4j/level2/another folder/yolo moved.txt")
        .run();

    assertTrue(sessionMega.ls(
        "megacmd4j/level2/another folder/yolo moved.txt"
    ).exists());
  }

  @DisplayName("Delete multiple files with mask: *")
  @Tag("exists")
  @Tag("rm")
  @Order(15)
  @Test()
  public void deleteMultipleFilesWithMaskShouldBeOk() {
    if (sessionMega.exists("megacmd4j/level2/yolo-*.txt")) {
      sessionMega.remove("megacmd4j/level2/yolo-*.txt").run();
    }

    assertEquals(
        1,
        sessionMega.count("megacmd4j/level2", FileInfo::isFile),
        "There should be only 1 file"
    );

    assertTrue(
        sessionMega.exists("megacmd4j/level2/level3"),
        "There should be left a directory"
    );
  }

  @DisplayName("Remove remote directory")
  @Tag("rm")
  @Order(16)
  @Test()
  public void shouldRemoveRemoteDirectory() {
    sessionMega.removeDirectory("megacmd4j/level2/level3").run();
  }

  @DisplayName("When remove nonexistent remote folder")
  @Tag("rm")
  @Order(17)
  @Test()
  public void givenNonexistentDirectoryWhenRemoveThenFail() {
    assertThrows(MegaException.class,
        () -> sessionMega.removeDirectory("megacmd4j/level2/level3").run());
  }

  @DisplayName("HTTPS should be disabled")
  @Tag("https")
  @Order(17)
  @Test()
  public void httpsShouldBeDisabled() {
    assertFalse(
        sessionMega.isHttpsEnabled(),
        "HTTPS should be disabled"
    );
  }

  @DisplayName("When share nonexistent remote folder then fail")
  @Tag("share")
  @Order(18)
  @Test()
  public void givenNonexistentDirectoryWhenShareThenFail() {
    String username = System.getenv(Mega.USERNAME_ENV_VAR);

    assertThrows(MegaResourceNotFoundException.class,
        () -> sessionMega.share("megacmd4j/unexisting-folder", username)
            .grantReadAndWriteAccess()
            .run());
  }

  @DisplayName("Share existing remote folder")
  @Tag("share")
  @Order(18)
  @Test()
  public void givenExistingDirectoryWhenShareThenSuccess() {
    String username = System.getenv(Mega.USERNAME_ENV_VAR);
    sessionMega.share("megacmd4j/level2", username)
        .grantReadAndWriteAccess()
        .run();
  }

  @DisplayName("When export nonexistent remote folder then fail")
  @Tag("export")
  @Order(19)
  @Test()
  public void givenNonexistentDirectoryWhenExportThenFail() {
    assertThrows(MegaResourceNotFoundException.class,
        () -> sessionMega.export("megacmd4j/unexisting-folder")
            .enablePublicLink()
            .call());
  }

  @DisplayName("Export existing remote folder")
  @Tag("export")
  @Order(19)
  @Test()
  public void givenExistingDirectoryWhenExportThenSuccess() {
    final ExportInfo exportInfo = sessionMega.export("megacmd4j/level2")
        .enablePublicLink()
        .call();

    assertEquals("/megacmd4j/level2", exportInfo.getRemotePath());

    assertTrue(
        exportInfo.getPublicLink().startsWith("https://mega.nz/"),
        "The exported public link should be located in MEGA.nz"
    );
    assertTrue(exportInfo.getPublicLink().length()
            - "https://mega.nz/".length() > 5,
        "The exported url should have more than 5 characters at least");
  }

  @DisplayName("Get information about exported folder")
  @Tag("export")
  @Order(20)
  @Test()
  public void exportedFolderShouldAppearInListings() {
    final List<ExportInfo> exportedFiles
        = sessionMega.export("megacmd4j").list();

    assertTrue(exportedFiles.stream()
        .map(ExportInfo::getRemotePath)
        .filter("megacmd4j/level2"::equals)
        .findAny().isPresent());
  }
}
