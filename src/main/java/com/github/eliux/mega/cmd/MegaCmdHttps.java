package com.github.eliux.mega.cmd;

import com.github.eliux.mega.MegaUtils;
import com.github.eliux.mega.error.MegaIOException;
import com.github.eliux.mega.error.MegaInvalidResponseException;

import java.io.IOException;
import java.util.Optional;

/**
 * Shows if HTTPS is used for transfers. Use {@link #MegaCmdHttps(boolean)} to
 * enable or disable HTTPS for transfers or {@link MegaCmdHttps} constructor
 * just to query the current state. Use {@link #call()} to see the result.
 */
public class MegaCmdHttps extends AbstractMegaCmdCallerWithParams<Boolean> {

    private final Optional<Boolean> enable;

    public MegaCmdHttps() {
        this.enable = Optional.empty();
    }

    public MegaCmdHttps(boolean enable) {
        this.enable = Optional.of(enable);
    }

    protected Optional<Boolean> parseResponseToHttpsEnabled(String response) {
        if (response.endsWith("HTTPS")) {
            return Optional.of(Boolean.TRUE);
        }

        if (response.endsWith("HTTP")) {
            return Optional.of(Boolean.FALSE);
        }

        return Optional.empty();
    }

    @Override
    public String getCmd() {
        return "https";
    }

    @Override
    public Boolean call() {
        try {
            final String response =
                    MegaUtils.execCmdWithSingleOutput(executableCommand());

            return parseResponseToHttpsEnabled(response)
                    .orElseThrow(() -> new MegaInvalidResponseException(
                            "Invalid HTTP state in the response"
                    ));
        } catch (IOException ex) {
            throw new MegaIOException();
        }
    }

    @Override
    String cmdParams() {
        return enable.map(isEnabled -> isEnabled ? "on" : "off")
                .orElse("");
    }
}
