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
  }

  @AfterClass
  public static void finishSession() {
    //sessionMega.logout();
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
  public void given_remotePath_when_Import_then_success() throws Exception {
    //Given
    // /megacmj/level1
    String responseWithRemotePath =
        "https://mega.nz/folder/ejYCBKBI#I0MDK6M3S-5rze9dk1oTow";

    //When
    final ImportInfo importInfo = sessionMega.importLink(responseWithRemotePath).call();

    //Then
    Assert.assertEquals("/level1", importInfo.getRemotePath());
  }


  @Test
  public void given_remotePath_destination_when_Import_then_success() throws Exception {
    //Given
    // /megacmd4j/folder2
    String responseWithRemotePath =
            "https://mega.nz/folder/SjZG1aZQ#8mx728HHlN7Ev9sLb1RARg";

    //When
    final ImportInfo importInfo = sessionMega.importLink(responseWithRemotePath, "/megacmd4j/testImport/").call();

    //Then
    Assert.assertEquals("/megacmd4j/testImport/folder2", importInfo.getRemotePath());
  }

  @Test
  public void given_remotePath_destination_and_password_when_Import_then_success() throws Exception {
    //Given
    // /megacmd4j/folder3
    String responseWithRemotePath = "https://mega.nz/#P!AgDqMhBNisD3c8H98Uw6TNjW1lmgCDzxf_BoXc680RCJelSaQnOnFn-kgFiK21t0afxDJTp_8NW81Jl1JfX9ePp_34VzI9Z5aMTjQkLGMl9ePAdN-gidRg";
    String key = "cemotdepasseestvraimentvraimentrobuste";

    //When
    final ImportInfo importInfo = sessionMega.importLink(responseWithRemotePath, "/megacmd4j/testImport/", key).call();

    //Then
    Assert.assertEquals("/megacmd4j/testImport/folder3", importInfo.getRemotePath());
  }


  @Test
  public void given_remotePath_password_when_Import_then_success() throws Exception {
    //Given
    // /megacmd4j/folder4
    String responseWithRemotePath = "https://mega.nz/#P!AgDuIHIAgtrWosqbzuTgQVBVYMnjlfZdiC3alPI_jXxQzn72-gA1kegotSG7hC8Sv8Ck1q9Nxkfv7F9_jSMTa6VbA8qAyZHOYohfJODN7DER3aGzxcE2XQ";
    String password = "cemotdepasseestvraimentvraimentrobuste";

    //When
    final ImportInfo importInfo = sessionMega.importLinkWithPassword(responseWithRemotePath,  password).call();

    //Then
    Assert.assertEquals("/folder4", importInfo.getRemotePath());
  }
}
