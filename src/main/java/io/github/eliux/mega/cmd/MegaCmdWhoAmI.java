package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;

import java.io.IOException;
import java.util.Optional;

public class MegaCmdWhoAmI extends AbstractMegaCmd<String> {

    @Override
    public String getCmd() {
        return "whoami";
    }

    @Override
    protected Optional<String> executeSysCmd(String cmdStr) {
        try {
            final String response = MegaUtils.execWithOutput(cmdStr).get(0);
            return Optional.ofNullable(response)
                    .flatMap(MegaCmdWhoAmI::parseUsername);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    static final Optional<String> parseUsername(String response) {
        return Optional.ofNullable(response)
                .map(s -> s.split("e-mail:"))
                .filter(x -> x.length == 2)
                .map(s -> s[1].trim());
    }
}
