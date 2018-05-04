package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

import java.io.IOException;
import java.util.Optional;

public class MegaCmdSession extends MegaCmd<String> {

    @Override
    public String getCmd() {
        return "mega-session";
    }

    @Override
    protected Optional<String> executeSysCmd(String cmdStr) {
        try {
            final String response = MegaUtils.execWithOutput(cmdStr).get(0);
            return Optional.ofNullable(response)
                    .flatMap(MegaCmdSession::parseSessionID);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    static final Optional<String> parseSessionID(String response) {
        return Optional.ofNullable(response)
                .map(s -> s.split(":"))
                .filter(x -> x.length == 2)
                .map(s -> s[1].trim());
    }
}
