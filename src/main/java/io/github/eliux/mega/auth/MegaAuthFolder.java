package io.github.eliux.mega.auth;

import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.cmd.MegaCmdLogin;

public class MegaAuthFolder extends MegaAuth {
    private final String folderPath;

    public MegaAuthFolder(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public MegaSession login() {
        final MegaCmdLogin megaCmdLogin = new MegaCmdLogin(folderPath);
        megaCmdLogin.call();

        return new MegaSession(this);
    }
}
