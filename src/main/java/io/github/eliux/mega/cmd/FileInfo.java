package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines a remote file/Folder in MEGA
 *
 * @see FileStatus
 */
public class FileInfo {

  private static final Pattern FILE_INFO_PATTERN = Pattern
      .compile("^(\\S{4})\\s+(\\S+)\\s+(-|\\d+)\\s(\\S+)\\s(\\S+)\\s(.+)$");

  private final String name;

  private final LocalDateTime date;

  private final Optional<Long> size;

  private final Optional<Integer> version;

  private final FileStatus status;

  public FileInfo(String name, LocalDateTime date, Optional<Long> size,
      Optional<Integer> version, FileStatus status) {
    this.name = name;
    this.date = date;
    this.size = size;
    this.version = version;
    this.status = status;
  }

  public static final FileInfo parseInfo(String fileInfoStr) {
    final Matcher matcher = FILE_INFO_PATTERN.matcher(fileInfoStr);
    try {
      if (matcher.find()) {
        return parseFileInfo(matcher);
      }
    } catch (Exception ex) {
      final MegaInvalidResponseException megaEx =
          new MegaInvalidResponseException(
              "Error while parsing file info from %s", fileInfoStr
          );
      megaEx.addSuppressed(ex);
      throw megaEx;
    }

    throw new MegaInvalidResponseException(fileInfoStr);
  }

  public static final boolean isValid(String fileInfoStr) {
    try {
      return FILE_INFO_PATTERN.matcher(fileInfoStr).matches();
    } catch (Exception ex) {
      return false;
    }
  }

  private static final FileInfo parseFileInfo(Matcher matcher) {
    String filename = parseFileName(matcher);
    final LocalDateTime date = parseDate(matcher);
    final Optional<Long> sizeInBytes = parseSizeInBytes(matcher);
    final Optional<Integer> version = parseVersion(matcher);
    final FileStatus fileStatus = parseFileType(matcher);

    return new FileInfo(filename, date, sizeInBytes, version, fileStatus);
  }

  private static final String parseFileName(Matcher matcher) {
    return matcher.group(6);
  }

  private static final LocalDateTime parseDate(Matcher matcher) {
    final String dateTimeStr = String.format(
        "%s %s",
        matcher.group(4),
        matcher.group(5)
    );
    return MegaUtils.parseFileDate(dateTimeStr);
  }

  private static final Optional<Long> parseSizeInBytes(Matcher matcher) {
    try {
      return Optional.of(matcher.group(3)).map(Long::valueOf);
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }

  private static final Optional<Integer> parseVersion(Matcher matcher) {
    try {
      return Optional.of(matcher.group(2)).map(Integer::valueOf);
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }

  private static final FileStatus parseFileType(Matcher matcher) {
    return FileStatus.valueOf(matcher.group(1));
  }

  public boolean isFile() {
    return status.getType() == FileStatus.Type.FILE;
  }

  public boolean isDirectory() {
    return status.getType() == FileStatus.Type.DIRECTORY;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Optional<Long> getSize() {
    return size;
  }

  public Optional<Integer> getVersion() {
    return version;
  }

  public FileStatus getStatus() {
    return status;
  }
}
