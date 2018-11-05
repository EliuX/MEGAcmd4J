package com.github.eliux.mega.cmd;

import com.github.eliux.mega.error.MegaInvalidResponseException;

/**
 * Defines the status of the remote file/folder in MEGA
 *
 * @see FileStatus
 */
public class FileStatus {

  private final Type type;
  private final boolean exported;
  private final boolean exportedPermanent;
  private final boolean exportedTemporal;
  private final SharingStatus sharingStatus;

  public FileStatus(Type type, boolean exported, boolean exportedPermanent,
      boolean exportedTemporal, SharingStatus sharingStatus) {
    this.type = type;
    this.exported = exported;
    this.exportedPermanent = exportedPermanent;
    this.exportedTemporal = exportedTemporal;
    this.sharingStatus = sharingStatus;
  }

  public static FileStatus valueOf(String statusStr) {
    if (statusStr.length() != 4) {
      throw new MegaInvalidResponseException(
          "'%s' dont contains valid flags for a file"
      );
    }

    final Type type = parseType(statusStr.charAt(0));
    final boolean isExported = isExported(statusStr.charAt(1));
    final boolean isExportedPermanent =
        isExportedPermanent(statusStr.charAt(2));
    final boolean isExportedTemporal =
        isExportedTemporal(statusStr.charAt(2));
    final SharingStatus sharingStatus =
        parseSharingStatus(statusStr.charAt(3));

    return new FileStatus(type, isExported, isExportedPermanent,
        isExportedTemporal, sharingStatus);
  }

  static private Type parseType(char c1st) {
    try {
      return Type.valueOf(c1st);
    } catch (IllegalArgumentException ex) {
      throw new MegaInvalidResponseException(
          "'%c' is not a valid file type", c1st
      );
    }
  }

  static private boolean isExported(Character c2nd) {
    return c2nd == 'e';
  }

  private static boolean isExportedPermanent(char c3rd) {
    return c3rd == 'p';
  }

  private static boolean isExportedTemporal(char c3rd) {
    return c3rd == 't';
  }

  private static SharingStatus parseSharingStatus(char c4th) {
    try {
      return SharingStatus.valueOf(c4th);
    } catch (IllegalArgumentException ex) {
      throw new MegaInvalidResponseException(
          "'%c' is not a valid sharing status", c4th
      );
    }
  }

  public Type getType() {
    return type;
  }

  public boolean isExported() {
    return exported;
  }

  public boolean isExportedPermanent() {
    return exportedPermanent;
  }

  public boolean isExportedTemporal() {
    return exportedTemporal;
  }

  public SharingStatus getSharingStatus() {
    return sharingStatus;
  }

  public enum Type {

    DIRECTORY('d'),
    FILE('-'),
    ROOT('r'),
    INBOX('i'),
    RUBBISH('b'),
    UNSUPORTED('x');

    char value;

    Type(char value) {
      this.value = value;
    }

    public static Type valueOf(char c)
        throws IllegalArgumentException {
      for (Type t : values()) {
        if (t.value == c) {
          return t;
        }
      }

      throw new IllegalArgumentException();
    }
  }

  public enum SharingStatus {
    SHARED('s'),
    IN_SHARE('I'),
    NOT_SHARED('-');

    char value;

    SharingStatus(char value) {
      this.value = value;
    }

    public static SharingStatus valueOf(char c)
        throws IllegalArgumentException {
      for (SharingStatus t : values()) {
        if (t.value == c) {
          return t;
        }
      }

      throw new IllegalArgumentException();
    }
  }
}
