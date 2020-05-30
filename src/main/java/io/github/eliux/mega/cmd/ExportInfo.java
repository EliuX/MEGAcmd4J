package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the information of an exported resource in MEGA
 */
public class ExportInfo {

    private static final Pattern LIST_PATTERN =
            Pattern.compile("(?<remotePath>\\S+) \\(.+link: (?<publicLink>http[s]?://mega.nz/\\S*#.+)\\)");

    private final String remotePath;

    private final String publicLink;

    public ExportInfo(String remotePath, String publicLink) {
        this.remotePath = remotePath;
        this.publicLink = publicLink;
    }

    public static ExportInfo parseExportInfo(String exportInfoStr) {
        final String[] tokens = exportInfoStr.replace("Exported ", "")
                .split(": ");
        return new ExportInfo(tokens[0], tokens[1]);
    }

    public static ExportInfo parseExportListInfo(String exportInfoLine) {
        try {
            final Matcher matcher = LIST_PATTERN.matcher(exportInfoLine);

            if (matcher.find()) {
                return new ExportInfo(
                        matcher.group("remotePath"),
                        matcher.group("publicLink")
                );
            }
        } catch (IllegalStateException | IndexOutOfBoundsException ex) {
            //Don't let it go outside
        }

        throw new MegaInvalidResponseException(exportInfoLine);
    }

    public String getRemotePath() {
        return remotePath;
    }

    public String getPublicLink() {
        return publicLink;
    }
}
