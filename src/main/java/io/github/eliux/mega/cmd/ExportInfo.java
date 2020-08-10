package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaInvalidExpireDateException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the information of an exported resource in MEGA
 */
public class ExportInfo {

    private static String dateFormat = "EEE, dd MMM yyyy HH:mm:ss Z";

    private static final Pattern LIST_PATTERN =
            Pattern.compile("(?<remotePath>\\S+) \\(.+link: (?<publicLink>http[s]?://mega.nz/\\S*#.+)\\)");

    private final String remotePath;

    private final String publicLink;

    private String expireDate;

    public ExportInfo(String remotePath, String publicLink) {
        this.remotePath = remotePath;
        this.publicLink = publicLink;
    }

    public ExportInfo(String remotePath, String publicLink, String expireDate) {
        this.remotePath = remotePath;
        this.publicLink = publicLink;
        this.expireDate = expireDate;
    }


    public static ExportInfo parseExportInfo(String exportInfoStr) {
        final String[] tokens = exportInfoStr.replace("Exported ", "")
                .split(": ");

        if (tokens[1].contains("expires at ")) {
            String[] publicToken = tokens[1].split("expires at ");

            String dateString = publicToken[1];
            if (isValidDateFormat(dateString)) {
                return new ExportInfo(tokens[0], publicToken[0].trim(), dateString);
            } else throw new MegaInvalidExpireDateException("Date should be in format: " + dateFormat);
        } else {
            return new ExportInfo(tokens[0], tokens[1]);
        }
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

    public static boolean isValidDateFormat(String value) {
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern(dateFormat, Locale.US));
            return true;
        } catch (DateTimeParseException ignored) {
        }

        return false;
    }

    public LocalDate getExpireDate() {
        if (expireDate != null) {
            return LocalDateTime.parse(expireDate, DateTimeFormatter.ofPattern(dateFormat, Locale.US)).toLocalDate();
        }

        return null;
    }

    public String getExpireDateAsString() {
        return expireDate;
    }
}
