package com.github.eliux.mega.error;

public class MegaInvalidResponseException extends MegaException {

  public MegaInvalidResponseException(String cmdName) {
    super(cmdName + " returned an invalid response");
  }

  public MegaInvalidResponseException(String errorMessage, Object... args) {
    super(errorMessage, args);
  }
}
