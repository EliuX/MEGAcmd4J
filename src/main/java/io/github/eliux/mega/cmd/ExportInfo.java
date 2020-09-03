package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the information of an exported resource in MEGA
 */
public class ExportInfo {

    private static final Pattern LIST_PATTERN =
            Pattern.compile("(?<remotePath>\\S+) \\(.+link: (?<publicLink>http[s]?://mega.nz/\\S*#.+)\\)");

    private static final Pattern EXPORT_PATTERN =
            Pattern.compile("\\s(?<remotePath>\\S+)(:)\\s(?<publicLink>http[s]?://mega.nz/\\S*#\\S*)( expires at (?<expireDate>.{3}, .{2} .{3} .{4} .{2}:.{2}:.{2} .+))?");

    private final String remotePath;

    private final String publicLink;

    private Optional<LocalDate> expireDate;

    public ExportInfo(String remotePath, String publicLink) {
        this.remotePath = remotePath;
        this.publicLink = publicLink;
    }

    public static ExportInfo parseExportInfo(String exportInfoStr) {
        final Matcher matcher = EXPORT_PATTERN.matcher(exportInfoStr);

        if (matcher.find()) {
            String remotePath = matcher.group("remotePath");
            String publicLink = matcher.group("publicLink");

            final ExportInfo result = new ExportInfo(remotePath, publicLink);
            Optional.ofNullable(matcher.group("expireDate")).ifPresent(result::setExpireDate);

            return result;
        }

        throw new MegaInvalidResponseException(exportInfoStr);
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

    public Optional<LocalDate> getExpireDate() {
        return expireDate;
    }

    public ExportInfo setExpireDate(String expireDate) {
        this.expireDate = Optional.of(MegaUtils.parseExpireDate(expireDate));
        return this;
    }
}
