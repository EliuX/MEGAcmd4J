package com.github.eliux.mega.cmd;

import com.github.eliux.mega.MegaUtils;
import com.github.eliux.mega.error.MegaException;
import com.github.eliux.mega.error.MegaIOException;
import com.github.eliux.mega.error.MegaResourceNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MegaCmdList extends AbstractMegaCmdCallerWithParams<List<FileInfo>> {

  private String remotePath;

  public MegaCmdList() {
    this.remotePath = "";
  }

  public MegaCmdList(String remotePath) {
    this.remotePath = remotePath;
  }

  @Override
  public String getCmd() {
    return "ls";
  }

  @Override
  String cmdParams() {
    return "-l " + remotePath;
  }

  @Override
  public List<FileInfo> call() {
    try {
      return MegaUtils.execCmdWithOutput(executableCommand()).stream().skip(1)
          .filter(FileInfo::isValid)  //To avoid complementary info
          .map(FileInfo::parseInfo)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new MegaIOException("Error while listing " + remotePath);
    }
  }

  public List<FileInfo> filter(Predicate<FileInfo> predicate) {
    try {
      return MegaUtils.execCmdWithOutput(executableCommand()).stream()
          .skip(1)                    // For sure the first is not valid
          .filter(FileInfo::isValid)  //To avoid complementary info
          .map(FileInfo::parseInfo)
          .filter(predicate)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new MegaIOException("Error while listing " + remotePath);
    }
  }

  public long count() throws MegaResourceNotFoundException {
    try {
      return MegaUtils.execCmdWithOutput(executableCommand()).stream()
          .filter(FileInfo::isValid)
          .count();
    } catch (IOException e) {
      throw new MegaIOException("Error while listing " + remotePath);
    }
  }

  public long count(Predicate<FileInfo> predicate)
      throws MegaResourceNotFoundException {
    try {
      return MegaUtils.execCmdWithOutput(executableCommand()).stream()
          .filter(FileInfo::isValid)  //To avoid complementary info
          .map(FileInfo::parseInfo)
          .filter(predicate)
          .count();
    } catch (IOException e) {
      throw new MegaIOException("Error while listing " + remotePath);
    }
  }

  public boolean exists() {
    try {
      MegaUtils.execCmdWithOutput(executableCommand());
      return true;
    } catch (MegaException e) {
      return false;
    } catch (IOException e) {
      throw new MegaIOException("Error while listing " + remotePath);
    }
  }
}
