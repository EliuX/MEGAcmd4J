package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.eliux.mega.Mega;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Parse MEGAcmd version")
@Tag("cmd")
@Tag("version")
public class MegaCmdVersionTest {

  @DisplayName("Get valid version id")
  @Test
  public void shouldReturnValidVersion() {
    final MegaCmdVersion.MegaCmdVersionResponse megaCmdVersion =
        Mega.version().call();

    assertFalse(
        megaCmdVersion.getVersion() == null
            || megaCmdVersion.getVersion().isEmpty(),
        "No MEGAcmd version was found"
    );
  }

  @DisplayName("Parse MEGAcmd features from the version output")
  @Test
  public void shouldReturnExpectedFeaturesEnabled() {
    final List<String> versionResponse = Arrays.asList(
        "MEGAcmd version: 1.1.0: code 1010000",
        "MEGA SDK version: 3.3.5",
        "etc",
        "Features enabled:",
        "* SQLite",
        "* FreeImage",
        "* Feature3"
    );

    final List<String> features = MegaCmdVersion.parseFeaturesEnabled(versionResponse);

    assertEquals("SQLite", features.get(0));
    assertEquals("FreeImage", features.get(1));
    assertEquals("Feature3", features.get(2));
  }

  @DisplayName("Parse extended version output")
  @Test
  public void shouldReturnExtendedVersion() {
    final MegaCmdVersion.MegaCmdVersionResponse version =
        Mega.version().showExtendedInfo().call();

    assertTrue(
        version instanceof MegaCmdVersion.MegaCmdVersionExtendedResponse,
        "Is not an extended response"
    );

    MegaCmdVersion.MegaCmdVersionExtendedResponse extendedVersion =
        (MegaCmdVersion.MegaCmdVersionExtendedResponse) version;

    assertFalse(
        extendedVersion.getVersion() == null
            || extendedVersion.getVersion().isEmpty(),
        "Invalid MEGAcmd version"
    );

    assertTrue(
        extendedVersion.getVersionCode().matches("\\d{3,}"),
        "version code had not 3 characters"
    );

    assertTrue(
        extendedVersion.getSdkVersion().matches(".{1,3}\\..{1,3}\\..{1,3}"),
        "The SDK version had not the expected format"
    );

    assertEquals(
        "https://github.com/meganz/sdk/blob/master/CREDITS.md",
        extendedVersion.getSdkCredits()
    );

    assertEquals(
        "https://github.com/meganz/sdk/blob/master/LICENSE",
        extendedVersion.getSdkLicense()
    );

    assertEquals(
        "https://github.com/meganz/megacmd/blob/master/LICENSE",
        extendedVersion.getLicense()
    );

    assertNotEquals(
        0,
        extendedVersion.getFeatures().size(),
        "MEGAcmd has no features enabled"
    );

    assertFalse(
        extendedVersion.getFeatures().get(0).startsWith("*"),
        "MEGAcmd features starts with a prefix: *"
    );
  }
}
