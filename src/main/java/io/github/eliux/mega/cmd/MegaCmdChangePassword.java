package io.github.eliux.mega.cmd;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MegaCmdChangePassword extends AbstractMegaCmdRunnerWithParams {

    private final String oldPassword;

    private final String newPassword;

    private Optional<String> authCode;

    public MegaCmdChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        authCode = Optional.empty();
    }

    @Override
    List<String> cmdParams() {
        final List<String> cmdParams = new LinkedList<>();

        cmdParams.add(oldPassword);

        cmdParams.add("-f");

        authCode.ifPresent(ac -> {
            cmdParams.add(String.format("--auth-code=%s", ac));
        });

        cmdParams.add(newPassword);

        return cmdParams;
    }

    @Override
    public String getCmd() {
        return "passwd";
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public MegaCmdChangePassword setAuthCode(String authCode) {
        this.authCode = Optional.of(authCode);
        return this;
    }

    public MegaCmdChangePassword removeAuthCode() {
        this.authCode = Optional.empty();
        return this;
    }
}
