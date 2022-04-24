package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaTestUtils;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ls")
class MegaCmdListTest extends AbstractRemoteTests {

  @DisplayName("List files with a whitespace in their name or not")
  @Test()
  public void shouldListFileWithSpaceInTheRootFolder() {
    MegaTestUtils.createTextFile(
        "target/test 123.txt",
        "Content of file with space"
    );
    MegaTestUtils.createTextFile(
        "target/test123.txt",
        "Content of file without space"
    );

    sessionMega.uploadFiles("/", "target/test123.txt", "target/test 123.txt")
        .run();

    final List<FileInfo> files = sessionMega.ls("/").call();

    Assertions.assertTrue(
        files.stream().filter(f -> f.getName().equals("test123.txt")).findAny().isPresent()
    );
    Assertions.assertTrue(
        files.stream().filter(f -> f.getName().equals("test 123.txt")).findAny().isPresent()
    );
    Assertions.assertFalse(
        files.stream().filter(f -> f.getName().equals("test123123123.txt")).findAny().isPresent()
    );
  }
}