package com.github.eliux.mega.error;

public class MegaCmdInvalidArguments extends MegaException {

  public MegaCmdInvalidArguments(String errorMessage) {
    super(errorMessage);
  }

  public MegaCmdInvalidArguments(String errorMessage, Object... args) {
    super(errorMessage, args);
  }
}
