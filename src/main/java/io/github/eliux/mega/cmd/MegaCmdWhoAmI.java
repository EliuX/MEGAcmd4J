package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaLoginRequiredException;

import java.io.IOException;
import java.util.Optional;

public class MegaCmdWhoAmI extends AbstractMegaCmdCaller<String> {

    @Override
    public String getCmd() {
        return "whoami";
    }

    @Override
    protected String executeSysCmd(String cmdStr) {
        try {
            final String response = MegaUtils.execWithOutput(cmdStr).get(0);

            return parseUsername(response).orElseThrow(
                    () -> new MegaLoginRequiredException()
            );
        } catch (IOException e) {
            throw new MegaIOException();
        }
    }

    static final Optional<String> parseUsername(String response) {
        return Optional.ofNullable(response)
                .map(s -> s.split("e-mail:"))
                .filter(x -> x.length == 2)
                .map(s -> s[1].trim());
    }
}
