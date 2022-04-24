package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MegaCmdExport extends AbstractMegaCmdCallerWithParams<ExportInfo> {

  private final Optional<String> remotePath;

  private Optional<String> password;

  private boolean exportDeleted;

  private boolean listOnly;

  Optional<TimeDelay> expirationTimeDelay;

  public MegaCmdExport(String remotePath) {
    this.remotePath = Optional.of(remotePath);
    this.password = Optional.empty();
    this.expirationTimeDelay = Optional.empty();
  }

  @Override
  List<String> cmdParams() {
    final List<String> cmdParams = new LinkedList<>();

    remotePath
        .filter(x -> !listOnly)
        .ifPresent(x -> {
          cmdParams.add(exportDeleted ? "-d" : "-a");
          expirationTimeDelay.map(td -> String.format("--expire=%s", td))
              .ifPresent(cmdParams::add);
          cmdParams.add("-f");
        });

    password.ifPresent(p -> cmdParams.add(String.format("--password=%s", p)));

    remotePath.ifPresent(cmdParams::add);

    return cmdParams;
  }

  @Override
  public String getCmd() {
    return "export";
  }

  @Override
  public ExportInfo call() {
    try {
      return MegaUtils.handleCmdWithOutput(executableCommandArray())
          .stream().findFirst()
          .map(ExportInfo::parseExportInfo)
          .orElseThrow(() -> new MegaInvalidResponseException(
              "Invalid response while exporting '%s'", remotePath
          ));
    } catch (IOException e) {
      throw new MegaIOException("Error while exporting " + remotePath);
    }
  }

  public List<ExportInfo> list() {
    justList();
    try {
      return MegaUtils.handleCmdWithOutput(executableCommandArray()).stream()
          .map((ExportInfo::parseExportListInfo))
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new MegaIOException("Error while exporting " + remotePath);
    }
  }

  public MegaCmdExport enablePublicLink() {
    exportDeleted = false;
    return this;
  }

  public MegaCmdExport removePublicLink() {
    exportDeleted = true;
    return this;
  }

  public boolean isExportDeleted() {
    return exportDeleted;
  }

  public MegaCmdExport setPassword(String password) {
    this.password = Optional.of(password);
    return this;
  }

  public MegaCmdExport removePassword() {
    this.password = Optional.empty();
    return this;
  }

  public MegaCmdExport setExpirationTimeDelay(TimeDelay expirationTimeDelay) {
    this.expirationTimeDelay = Optional.of(expirationTimeDelay);
    return this;
  }

  public MegaCmdExport setExpireDate(LocalDateTime endDateTimeExclusive) {
    final Period period = Period.between(LocalDate.now(), endDateTimeExclusive.toLocalDate());
    final Duration duration = Duration.between(LocalDateTime.now(), endDateTimeExclusive);

    return this.setExpirationTimeDelay(TimeDelay.of(period, duration));
  }

  public MegaCmdExport setExpireDate(LocalDate date) {
    final Period period = Period.between(LocalDate.now(), date);
    return this.setExpirationTimeDelay(TimeDelay.of(period));
  }

  public MegaCmdExport withoutExpiration() {
    this.expirationTimeDelay = Optional.empty();
    return this;
  }

  protected MegaCmdExport justList() {
    listOnly = true;
    return this;
  }
}
