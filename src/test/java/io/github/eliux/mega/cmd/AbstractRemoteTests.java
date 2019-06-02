package io.github.eliux.mega.cmd;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

@Tag("remote")
public class AbstractRemoteTests {

  protected static MegaSession sessionMega;

  @BeforeAll
  static void setupSession() {
    sessionMega = Mega.init();
  }

  @AfterAll
  static void finishSession() {
    sessionMega.logout();
  }
}
