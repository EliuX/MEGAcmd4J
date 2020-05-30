package io.github.eliux.mega.error;

public class MegaResourceAlreadyExistsException extends MegaWrongArgumentsException {

  public MegaResourceAlreadyExistsException() {
    super("Existent resource");
  }
}

