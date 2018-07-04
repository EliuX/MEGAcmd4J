package com.github.eliux.mega.error;

public class MegaConfirmationRequiredException extends MegaException {

  public MegaConfirmationRequiredException() {
    super("Mega requires confirmation");
  }
}
