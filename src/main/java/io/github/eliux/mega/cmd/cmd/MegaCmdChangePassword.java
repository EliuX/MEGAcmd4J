package io.github.eliux.mega.cmd.cmd;

public class MegaCmdChangePassword extends AbstractMegaCmdRunnerWithParams {

    private final String oldPassword;

    private final String newPassword;

    public MegaCmdChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    String cmdParams() {
        return oldPassword + " " + newPassword;
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
}
