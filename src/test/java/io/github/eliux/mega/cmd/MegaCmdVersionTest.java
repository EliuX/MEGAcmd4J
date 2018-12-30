package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MegaCmdVersionTest {


    @Test
    public void shouldReturnValidVersion() {
        final MegaCmdVersion.MegaCmdVersionResponse megaCmdVersion =
                Mega.version().call();

        Assert.assertTrue(
                "Invalid MEGAcmd version",
                megaCmdVersion.getVersion().matches(
                        ".{1,3}\\..{1,3}\\..{1,3}"
                )
        );

        Assert.assertTrue(
                "version code had not 5 characters",
                megaCmdVersion.getVersionCode().matches("\\d{5}")
        );
    }

    @Test
    public void shouldReturnExpectedFeaturesEnabled() {
        final List<String> versionResponse = Arrays.asList(
                "MEGA CMD version: 0.9.9: code 90900",
                "MEGA SDK version: 3.3.5",
                "etc",
                "Features enabled:",
                "* SQLite",
                "* FreeImage",
                "* Feature3"
        );

        final List<String> features = MegaCmdVersion.parseFeaturesEnabled(versionResponse);

        Assert.assertEquals("SQLite", features.get(0));
        Assert.assertEquals("FreeImage", features.get(1));
        Assert.assertEquals("Feature3", features.get(2));
    }

    @Test
    public void shouldReturnExtendedVersion() {
        final MegaCmdVersion.MegaCmdVersionResponse version =
                Mega.version().showExtendedInfo().call();

        Assert.assertTrue(
                "Is not an extended response",
                version instanceof MegaCmdVersion.MegaCmdVersionExtendedResponse
        );

        MegaCmdVersion.MegaCmdVersionExtendedResponse extendedVersion =
                (MegaCmdVersion.MegaCmdVersionExtendedResponse) version;

        Assert.assertTrue(
                "The SDK version had not the expected format",
                extendedVersion.getSdkVersion().matches(".{1,3}\\..{1,3}\\..{1,3}")
        );

        Assert.assertEquals(
                "https://github.com/meganz/sdk/blob/master/CREDITS.md",
                extendedVersion.getSdkCredits()
        );

        Assert.assertEquals(
                "https://github.com/meganz/sdk/blob/master/LICENSE",
                extendedVersion.getSdkLicense()
        );

        Assert.assertEquals(
                "https://github.com/meganz/megacmd/blob/master/LICENSE",
                extendedVersion.getLicense()
        );

        Assert.assertNotEquals(
                "MEGAcmd has no features enabled",
                0,
                extendedVersion.getFeatures().size()
        );

        Assert.assertFalse(
                "MEGAcmd features starts with a prefix: *",
                extendedVersion.getFeatures().get(0).startsWith("*")
        );
    }
}
