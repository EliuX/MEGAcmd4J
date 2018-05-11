package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExportInfoTest {

    static MegaSession sessionMega;

    @BeforeClass
    public static void setupSession() {
        sessionMega = Mega.init();
    }

    @Test
    public void parseExportInfoShouldBeOk() {
        //Given
        String validExportInfo = "Exported /megacmd4j/level2: https://mega.nz/#F!8OQxgYgY!xxxg0kyQ9wibextVq5FZbQ";

        //When
        final ExportInfo exportInfo = ExportInfo.parseExportInfo(validExportInfo);

        //Then
        Assert.assertNotNull(exportInfo);
        Assert.assertEquals("/megacmd4j/level2", exportInfo.getRemotePath());
        Assert.assertEquals("https://mega.nz/#F!8OQxgYgY!xxxg0kyQ9wibextVq5FZbQ", exportInfo.getPublicLink());
    }

    @Test(expected = MegaInvalidResponseException.class)
    public void given_invalid_remotePath_when_parseExportListInfo_then_fail() {
        //Given
        String entryWithInvalidRemotePath = "megacmd4j/level2(folder, shared as exported " +
                " folder link: https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        ExportInfo.parseExportListInfo(entryWithInvalidRemotePath);
    }

    @Test(expected = MegaInvalidResponseException.class)
    public void given_invalid_publicLinkPrefix_when_parseExportListInfo_then_fail() {
        //Given
        String entryWithInvalidRemotePath = "megacmd4j/level2 (folder, shared as exported " +
                " folder content: https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        ExportInfo.parseExportListInfo(entryWithInvalidRemotePath);
    }

    @Test(expected = MegaInvalidResponseException.class)
    public void given_invalid_ending_when_parseExportListInfo_then_fail() {
        //Given
        String entryWithInvalidRemotePath = "megacmd4j/level2 (folder, shared as exported " +
                " folder content: https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA";

        //When
        ExportInfo.parseExportListInfo(entryWithInvalidRemotePath);
    }

    @Test(expected = MegaInvalidResponseException.class)
    public void given_publicLinkWithIncorrectMegaUrl_when_parseExportListInfo_then_fail() {
        //Given
        String responseWithInvalidMegaUrl =
                "megacmd4j (folder, shared as exported permanent " +
                        "folder link: http://mega.com/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        ExportInfo.parseExportListInfo(responseWithInvalidMegaUrl);
    }

    @Test
    public void given_remotePathWithSubPaths_when_parseExportListInfo_then_success() {
        //Given
        String responseWithRemotePathWithSubPaths =
                "megacmd4j/level2/level3 (folder, shared as exported permanent " +
                        "folder link: https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        final ExportInfo exportInfo =
                ExportInfo.parseExportListInfo(responseWithRemotePathWithSubPaths);

        //Then
        Assert.assertEquals("megacmd4j/level2/level3", exportInfo.getRemotePath());
        Assert.assertEquals(
                "https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA",
                exportInfo.getPublicLink()
        );
    }

    @Test
    public void given_remotePathWithSingleFolder_when_parseExportListInfo_then_success() {
        //Given
        String responseWithRemotePathWithSingleFolder =
                "megacmd4j (folder, shared as exported permanent " +
                        "folder link: https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        final ExportInfo exportInfo =
                ExportInfo.parseExportListInfo(responseWithRemotePathWithSingleFolder);

        //Then
        Assert.assertEquals("megacmd4j", exportInfo.getRemotePath());
        Assert.assertEquals(
                "https://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA",
                exportInfo.getPublicLink()
        );
    }

    @Test
    public void given_publicLinkNotHttps_when_parseExportListInfo_then_success() {
        //Given
        String responseWithHttp =
                "megacmd4j (folder, shared as exported permanent " +
                        "folder link: http://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA)";

        //When
        final ExportInfo exportInfo =
                ExportInfo.parseExportListInfo(responseWithHttp);

        //Then
        Assert.assertEquals("megacmd4j", exportInfo.getRemotePath());
        Assert.assertEquals(
                "http://mega.nz/#F!APJmCbiJ!lfKu3tVd8pNceLoH6qe_tA",
                exportInfo.getPublicLink()
        );
    }

    @AfterClass
    public static void finishSession() {
        sessionMega.logout();
    }
}
