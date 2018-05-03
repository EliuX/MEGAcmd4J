package io.github.eliux.mega;

import io.github.eliux.mega.auth.MegaAuth;
import io.github.eliux.mega.cmd.MegaCmdLogout;
import io.github.eliux.mega.cmd.MegaCmdSession;

public class MegaSession {

    private MegaAuth authentication;

    public MegaSession(MegaAuth authentication) {
        this.authentication = authentication;
    }

    public MegaAuth getAuthentication() {
        return authentication;
    }

    public void logout() {
        new MegaCmdLogout().call();
    }

    public String sessionID(){
        return new MegaCmdSession().call();
    }
}
