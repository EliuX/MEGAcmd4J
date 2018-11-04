package io.github.eliux.mega.auth;

import com.github.eliux.mega.MegaSession;
import io.github.eliux.mega.cmd.cmd.MegaCmdLogin;
import io.github.eliux.mega.error.MegaLoginException;

/**
 * Authenticates the users into MEGA just for an exported or public folder
 */
public class MegaAuthFolder extends MegaAuth {

    private final String folderPath;

    public MegaAuthFolder(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public MegaSession login() throws MegaLoginException {
        try {
            final MegaCmdLogin megaCmdLogin = new MegaCmdLogin(folderPath);
            megaCmdLogin.run();
            return new MegaSession(this);
        } catch (Throwable cause) {
            throw new MegaLoginException(
                    "You could not access to ".concat(folderPath), cause
            );
        }
    }
}
