package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImportInfoTest {

  private static MegaSession sessionMega;

  @BeforeClass
  public static void setupSession() {
    sessionMega = Mega.init();
    removeTestResourcesIfExist();
    sessionMega.makeDirectory("megacmd4j/testImport")
        .recursively()
        .run();
  }

  @AfterClass
  public static void finishSession() {
    removeTestResourcesIfExist();
    sessionMega.logout();
  }

  private static void removeTestResourcesIfExist() {
    sessionMega.removeDirectory("megacmd4j")
        .ignoreErrorIfNotPresent()
        .run();
  }


  @Test(expected = MegaInvalidResponseException.class)
  public void given_invalid_remotePath_when_parseImportInfo_then_fail() {
    //Given
    String entryWithInvalidRemotePath = "Imported: /test";

    //When
    ImportInfo.parseImportInfo(entryWithInvalidRemotePath);
  }

  @Test
  public void given_valid_remotePath_when_parseImportInfo_then_success() {
    //Given
    String entryWithInvalidRemotePath = "Imported folder complete: /test";

    //When
    ImportInfo result = ImportInfo.parseImportInfo(entryWithInvalidRemotePath);

    Assert.assertEquals(result.getRemotePath(), "/test");
  }


  @Test
  public void given_remotePath_without_key_when_import_then_create_directory_NO_KEY()
      throws Exception {
    //Given
    sessionMega.makeDirectory("megacmd4j/sampleDirToImportWithoutRemotePath")
        .recursively()
        .run();
    final ExportInfo exportInfo = sessionMega.export("megacmd4j/sampleDirToImportWithoutRemotePath")
        .enablePublicLink()
        .call();

    //When
    final ImportInfo importInfo = sessionMega.importLink(exportInfo.getPublicLink()).call();

    //Then
    Assert.assertEquals("/NO_KEY", importInfo.getRemotePath());

    //After
    sessionMega.removeDirectory("/NO_KEY")
        .ignoreErrorIfNotPresent()
        .run();
  }


  @Test
  public void given_remotePath_destination_when_import_without_password_then_create_directory_NO_KEY()
      throws Exception {
    //Given
    sessionMega.makeDirectory("megacmd4j/sampleDirToImportWithRemotePath")
        .recursively()
        .run();
    final ExportInfo exportInfo = sessionMega.export("megacmd4j/sampleDirToImportWithRemotePath")
        .enablePublicLink()
        .call();

    //When
    final ImportInfo importInfo =
        sessionMega.importLink(exportInfo.getPublicLink())
            .setRemotePath("megacmd4j/testImport/")
            .call();

    //Then
    Assert.assertEquals("/megacmd4j/testImport/NO_KEY", importInfo.getRemotePath());
  }


  @Test
  public void given_remotePath_destination_and_password_when_import_then_success()
      throws Exception {
    //This requires Pro User

    //Given
    // /megacmd4j/folder3
    String responseWithRemotePath = "https://mega.nz/#P!AgDqMhBNisD3c8H98Uw6TNjW1lmgCDzxf_BoXc680RCJelSaQnOnFn-kgFiK21t0afxDJTp_8NW81Jl1JfX9ePp_34VzI9Z5aMTjQkLGMl9ePAdN-gidRg";
    String key = "cemotdepasseestvraimentvraimentrobuste";

    //When
    final ImportInfo importInfo =
        sessionMega.importLink(responseWithRemotePath)
            .setRemotePath("/megacmd4j/testImport/")
            .setPassword(key)
            .call();

    //Then
    Assert.assertEquals("/megacmd4j/testImport/folder3", importInfo.getRemotePath());
  }


  @Test
  public void given_remotePath_password_when_import_then_success() throws Exception {
    //Given
    // /megacmd4j/folder4
    String responseWithRemotePath = "https://mega.nz/#P!AgDuIHIAgtrWosqbzuTgQVBVYMnjlfZdiC3alPI_jXxQzn72-gA1kegotSG7hC8Sv8Ck1q9Nxkfv7F9_jSMTa6VbA8qAyZHOYohfJODN7DER3aGzxcE2XQ";
    String password = "cemotdepasseestvraimentvraimentrobuste";

    //When
    final ImportInfo importInfo =
        sessionMega.importLink(responseWithRemotePath)
            .setPassword(password)
            .call();

    //Then
    Assert.assertEquals("/folder4", importInfo.getRemotePath());

    //After
    sessionMega.removeDirectory("/folder4")
        .ignoreErrorIfNotPresent()
        .run();
  }
}
