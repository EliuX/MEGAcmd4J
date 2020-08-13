package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidExpireDateException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the information of an exported resource in MEGA
 */
public class ExportInfo {

    private static String dateFormat = "EEE, dd MMM yyyy HH:mm:ss Z";
    private static final Pattern LIST_PATTERN =
            Pattern.compile("(?<remotePath>\\S+) \\(.+link: (?<publicLink>http[s]?://mega.nz/\\S*#.+)\\)");

    private static final Pattern EXPORT_PATTERN =
            Pattern.compile("\\s(?<remotePath>\\S+)(:)\\s(?<publicLink>http[s]?://mega.nz/\\S*#\\S*)( expires at (?<expireDate>.{3}, .{2} .{3} .{4} .{2}:.{2}:.{2} .+))?");

    private final String remotePath;

    private final String publicLink;

    private Optional<String> expireDate;

    public ExportInfo(String remotePath, String publicLink) {
        this.remotePath = remotePath;
        this.publicLink = publicLink;
    }

    public static ExportInfo parseExportInfo(String exportInfoStr) {
        final Matcher matcher = EXPORT_PATTERN.matcher(exportInfoStr);

        if (matcher.find()) {
            String remotePath = matcher.group("remotePath");
            String publicLink = matcher.group("publicLink");
            String expireDate = matcher.group("expireDate");

            if (expireDate == null && exportInfoStr.contains("expires at")) {
                throw new MegaInvalidExpireDateException(exportInfoStr);
            }
            if (expireDate != null) {
                return new ExportInfo(remotePath, publicLink)
                        .setExpireDate(expireDate);
            }  else {
                return new ExportInfo(remotePath, publicLink);
            }
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

    public LocalDate getExpireDate() {
        return expireDate.map(s -> LocalDateTime.parse(s, DateTimeFormatter.ofPattern(dateFormat, Locale.US)).toLocalDate()).orElse(null);
    }

    public ExportInfo setExpireDate(String expireDate) {
        this.expireDate = Optional.of(expireDate);
        return this;
    }
}
