package io.github.eliux.mega.cmd.cmd;

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
  protected String cmdParams() {
    final StringBuilder cmdParamsBuilder = new StringBuilder();

    cmdParamsBuilder.append(username).append(" ")
        .append(password).append(" ");

    name.ifPresent(n -> cmdParamsBuilder.append(
        String.format("--name=%s", n)
    ));

    return cmdParamsBuilder.toString();
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
