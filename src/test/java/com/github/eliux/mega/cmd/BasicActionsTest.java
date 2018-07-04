package com.github.eliux.mega.cmd;

import com.github.eliux.mega.Mega;
import com.github.eliux.mega.MegaSession;
import com.github.eliux.mega.MegaTestUtils;
import com.github.eliux.mega.error.MegaException;
import com.github.eliux.mega.error.MegaInvalidStateException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicActionsTest {

  private static MegaSession sessionMega;

  @BeforeClass
  public static void setupSession() {
    sessionMega = Mega.init();
  }

  @Test
  public void stage00_shouldDeleteWorkingDirectoryIfExist() {
    sessionMega.remove("megacmd4j")
        .deleteRecursively()
        .ignoreErrorIfNotPresent()
        .run();
  }

  @Test
  public void stage00_sessionShouldHaveAuthenticationObject() {
    Assert.assertNotNull(sessionMega.getAuthentication());
  }

  @Test
  public void stage01_shouldUploadFileToRoot() {
    MegaTestUtils.createTextFile("target/yolo-test.txt", "You only live once...");

    sessionMega.uploadFile("target/yolo-test.txt")
        .run();

    MegaTestUtils.removeFile("target/yolo-test.txt");
  }

  @Test
  public void stage02_shouldUploadFileToTargetFolder() {
    MegaTestUtils.createTextFile("target/yolo-infinite.txt", "You only live infinitive times...");

    sessionMega.uploadFile("target/yolo-infinite.txt", "megacmd4j/")
        .createRemoteIfNotPresent()
        .run();
  }

  @Test
  public void stage03_shouldUploadMultipleFilesAndCreateRemoteFolderSuccessfully() {
    MegaTestUtils.createTextFiles("yolo", 10);

    final MegaCmdPutMultiple megaCmd = sessionMega.uploadFiles("megacmd4j/")
        .createRemoteIfNotPresent();

    IntStream.rangeClosed(1, 10).forEach(i -> {
      String filename = MegaTestUtils.testTextFileName("yolo", i);
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

  @Test(expected = MegaException.class)
  public void stage06_given_multilevelfolder_when_mkdir_withRecursivelyAndthrowErrorIfExistsFlag_then_fail() {
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

  @Test(expected = MegaException.class)
  public void stage09_given_localPathDoesntExist_when_get_then_fail() {
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
  public void stage13_moveMultipleFilesUsingPatternIntoSubpath() {
    sessionMega.move("megacmd4j/*-*.txt", "megacmd4j/level2/")
        .run();

    final List<FileInfo> currentFiles = sessionMega.ls("megacmd4j/").call();
    Assert.assertEquals(
        "Only 2 files were expected",
        2,
        currentFiles.size()
    );

    final long amountOfDirectoriesLeft = currentFiles.stream()
        .filter(FileInfo::isDirectory).count();
    Assert.assertEquals(
        "There should be only 1 directory left",
        1,
        amountOfDirectoriesLeft
    );

    final long amountOfFilesLeft = currentFiles.stream()
        .filter(FileInfo::isFile).count();
    Assert.assertEquals(
        "There should be only 1 file left",
        1,
        amountOfFilesLeft
    );
  }

  @Test
  public void stage14_given_emptyfolder_when_ls_then_exists_is_true() {
    Assert.assertTrue(
        "The directory level3 should exist",
        sessionMega.ls("megacmd4j/level2/level3").exists()
    );
  }

  @Test
  public void stage15_given_nonExistingFile_when_ls_then_exists_is_false() {
    Assert.assertFalse(
        "That file/directory doesnt exist",
        sessionMega.ls("megacmd4j/level2/level33").exists()
    );
  }

  @Test
  public void stage15_deleteMultipleFilesWithMaskShouldBeOk() {
      if (sessionMega.exists("megacmd4j/level2/yolo-*.txt")) {
          sessionMega.remove("megacmd4j/level2/yolo-*.txt").run();
      }

    Assert.assertEquals(
        "There should be only 1 file",
        1,
        sessionMega.count("megacmd4j/level2", FileInfo::isFile)
    );

    Assert.assertTrue(
        "There should be left a directory",
        sessionMega.exists("megacmd4j/level2/level3")
    );
  }

  @Test
  public void stage16_given_directory_when_remove_then_success() {
    sessionMega.removeDirectory("megacmd4j/level2/level3").run();
  }

  @Test(expected = MegaException.class)
  public void stage17_given_unexistingDirectory_when_remove_then_fail() {
    sessionMega.removeDirectory("megacmd4j/level2/level3").run();
  }

  @Test(expected = MegaException.class)
  public void stage18_given_unexistingDirectory_when_share_then_fail() {
    String username = System.getenv(Mega.USERNAME_ENV_VAR);
    sessionMega.share("megacmd4j/unexisting-folder", username)
        .grantReadAndWriteAccess()
        .run();
  }

  @Test
  public void stage18_given_existingDirectory_when_share_then_success() {
    String username = System.getenv(Mega.USERNAME_ENV_VAR);
    sessionMega.share("megacmd4j/level2", username)
        .grantReadAndWriteAccess()
        .run();
  }

  @Test(expected = MegaException.class)
  public void stage19_given_unexistingDirectory_when_export_then_fail() {
    sessionMega.export("megacmd4j/unexisting-folder")
        .enablePublicLink()
        .call();
  }

  @Test
  public void stage19_given_existingDirectory_when_export_then_success() {
    final ExportInfo exportInfo = sessionMega.export("megacmd4j/level2")
        .enablePublicLink()
        .call();

    Assert.assertEquals("/megacmd4j/level2", exportInfo.getRemotePath());

    Assert.assertTrue(exportInfo.getPublicLink().startsWith("https://mega.nz/"));
    Assert.assertTrue(exportInfo.getPublicLink().length()
        - "https://mega.nz/".length() > 5);
  }

  @Test
  public void stage20_exportedFolderShouldAppearInListings() {
    final List<ExportInfo> exportedFiles
        = sessionMega.export("megacmd4j").list();

    Assert.assertTrue(exportedFiles.stream()
        .map(ExportInfo::getRemotePath)
        .filter("megacmd4j/level2"::equals)
        .findAny().isPresent());
  }

  @AfterClass
  public static void finishSession() {
    sessionMega.removeDirectory("megacmd4j").run();
    sessionMega.remove("yolo*.txt").run();

    sessionMega.logout();
  }
}
