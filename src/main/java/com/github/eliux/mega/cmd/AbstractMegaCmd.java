package com.github.eliux.mega.cmd;

import com.github.eliux.mega.platform.OSPlatform;

public abstract class AbstractMegaCmd<T> {

  protected String executableCommand() {
    return getCmdAdaptedToPlatform();
  }

  protected String getCmdAdaptedToPlatform() {
    return OSPlatform.getCurrent().cmdInstruction(getCmd());
  }

  public abstract String getCmd();
}
