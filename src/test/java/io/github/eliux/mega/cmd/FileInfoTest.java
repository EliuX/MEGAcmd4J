package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Parse FileInfo")
@Tag("ls")
public class FileInfoTest {

  @DisplayName("Parse FileInfo from string: ----    1         50 04May2018 17:54:11 yolo-2.txt")
  @Test
  public void givenFileWhenValueOfThenShouldBeOk() {
    //Given
    String fileInfoStr = "----    1         50 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    assertNotNull(fileInfo);
    assertTrue(fileInfo.isFile());
    assertEquals("yolo-2.txt", fileInfo.getName());
    assertEquals(
        MegaUtils.parseFileDate("04May2018 17:54:11"),
        fileInfo.getDate()
    );
    assertEquals(Optional.of(50L), fileInfo.getSize());
    assertEquals(Optional.of(1), fileInfo.getVersion());
  }

  @DisplayName("Parse FileInfo from string: d---    -          - 31Jan2018 00:25:12 megacmd4j")
  @Test
  public void givenDirectoryWhenValueOfThenShouldBeOk() {
    //Given
    String fileInfoStr = "d---    -          - 31Jan2018 00:25:12 megacmd4j";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    assertNotNull(fileInfo);
    assertTrue(fileInfo.isDirectory());
    assertEquals("megacmd4j", fileInfo.getName());
    assertEquals(
        MegaUtils.parseFileDate("31Jan2018 00:25:12"),
        fileInfo.getDate()
    );
    assertFalse(fileInfo.getSize().isPresent());
    assertFalse(fileInfo.getVersion().isPresent());
  }

  @DisplayName("Parse FileInfo from string: ----    -         50 04May2018 17:54:11 yolo-2.txt")
  @Test
  public void ifNoVersionItShouldBeOk() {
    //Given
    String fileInfoStr = "----    -         50 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    assertNotNull(fileInfo);
    assertFalse(fileInfo.getVersion().isPresent());
  }

  @DisplayName("Parse FileInfo from string: ----    1         - 04May2018 17:54:11 yolo-2.txt")
  @Test
  public void ifNoSizeItShouldBeOk() {
    //Given
    String fileInfoStr = "----    1         - 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    assertNotNull(fileInfo);
    assertFalse(fileInfo.getSize().isPresent());
  }

  @DisplayName("Parse FileInfo from string should fail: ----    1         50 04May20xx 17:54:11 yolo-2.txt")
  @Test()
  public void valueOfShouldFailIfDateIsIncorrect() {
    String fileInfoStr = "----    1         50 04May20xx 17:54:11 yolo-2.txt";

    assertThrows(MegaInvalidResponseException.class,
        () -> FileInfo.parseInfo(fileInfoStr));
  }

  @DisplayName("Parse FileInfo from string should fail: ----    1         50 04May20xx 17:54:11")
  @Test()
  public void valueOfShouldFailIfSizeOfResponseIsNot6() {
    String fileInfoStr = "----    1         50 04May20xx 17:54:11";

    assertThrows(MegaInvalidResponseException.class,
        () -> FileInfo.parseInfo(fileInfoStr));
  }

  @DisplayName("Parse token of FileInfo where name has spaces")
  @Test
  public void parseTokenWithFileWithSpacesShouldBeOk() {
    final String fileInfoString = "d---    -          - 29Jun2021 21:01:04 level 3 with spaces";

    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoString);

    Assertions.assertEquals(fileInfo.getName(), "level 3 with spaces");
  }
}
