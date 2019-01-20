package io.github.eliux.mega.cmd;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MegaCmdSignup extends AbstractMegaCmdRunnerWithParams {

  private final String username;

  private final String password;

  private Optional<String> name;

  public MegaCmdSignup(String username, String password) {
    super();
    this.username = username;
    this.password = password;
    this.name = Optional.empty();
  }

  @Override
  List<String> cmdParams() {
    final List<String> cmdParams = new LinkedList<>();

    cmdParams.add(username);

    cmdParams.add(password);

    name.ifPresent(n -> cmdParams.add(
        String.format("--name=%s", n)
    ));

    return cmdParams;
  }

  public Optional<String> getName() {
    return name;
  }

  public MegaCmdSignup setName(String name){
    this.name = Optional.of(name);
    return this;
  }

  @Override
  public String getCmd() {
    return "signup";
  }
}
