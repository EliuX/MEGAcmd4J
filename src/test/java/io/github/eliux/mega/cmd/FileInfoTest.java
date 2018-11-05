package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class FileInfoTest {

  @Test
  public void given_file_when_valueOf_then_ShouldBeOk() {
    //Given
    String fileInfoStr = "----    1         50 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    Assert.assertNotNull(fileInfo);
    Assert.assertTrue(fileInfo.isFile());
    Assert.assertEquals("yolo-2.txt", fileInfo.getName());
    Assert.assertEquals(
        MegaUtils.parseFileDate("04May2018 17:54:11"),
        fileInfo.getDate()
    );
    Assert.assertEquals(Optional.of(50L), fileInfo.getSize());
    Assert.assertEquals(Optional.of(1), fileInfo.getVersion());
  }

  @Test
  public void given_directory_when_valueOf_then_ShouldBeOk() {
    //Given
    String fileInfoStr = "d---    -          - 31Jan2018 00:25:12 megacmd4j";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    Assert.assertNotNull(fileInfo);
    Assert.assertTrue(fileInfo.isDirectory());
    Assert.assertEquals("megacmd4j", fileInfo.getName());
    Assert.assertEquals(
        MegaUtils.parseFileDate("31Jan2018 00:25:12"),
        fileInfo.getDate()
    );
    Assert.assertFalse(fileInfo.getSize().isPresent());
    Assert.assertFalse(fileInfo.getVersion().isPresent());
  }

  @Test
  public void ifNoVersionItShouldBeOk() {
    //Given
    String fileInfoStr = "----    -         50 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    Assert.assertNotNull(fileInfo);
    Assert.assertFalse(fileInfo.getVersion().isPresent());
  }

  @Test
  public void ifNoSizeItShouldBeOk() {
    //Given
    String fileInfoStr = "----    1         - 04May2018 17:54:11 yolo-2.txt";

    //When
    final FileInfo fileInfo = FileInfo.parseInfo(fileInfoStr);

    //Then
    Assert.assertNotNull(fileInfo);
    Assert.assertFalse(fileInfo.getSize().isPresent());
  }

  @Test(expected = MegaInvalidResponseException.class)
  public void valueOfShouldFailIfDateIsIncorrect() {
    //Given
    String fileInfoStr = "----    1         50 04May20xx 17:54:11 yolo-2.txt";

    //When
    FileInfo.parseInfo(fileInfoStr);
  }

  @Test(expected = MegaInvalidResponseException.class)
  public void valueOfShouldFailIfSizeOfResponseIsNot6() {
    //Given
    String fileInfoStr = "----    1         50 04May20xx 17:54:11";

    //When
    FileInfo.parseInfo(fileInfoStr);
  }
}
