package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.time.LocalDateTime;
import java.util.Optional;

public class FileInfo {

    private final String name;

    private final LocalDateTime date;

    private final Optional<Long> size;

    private final Optional<Integer> version;

    private final FileStatus status;

    public FileInfo(String name, LocalDateTime date, Optional<Long> size,
                    Optional<Integer> version, FileStatus status) {
        this.name = name;
        this.date = date;
        this.size = size;
        this.version = version;
        this.status = status;
    }

    public boolean isFile() {
        return status.getType() == FileStatus.Type.FILE;
    }

    public boolean isDirectory() {
        return status.getType() == FileStatus.Type.DIRECTORY;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Optional<Long> getSize() {
        return size;
    }

    public Optional<Integer> getVersion() {
        return version;
    }

    public FileStatus getStatus() {
        return status;
    }

    public static final FileInfo valueOf(String fileInfoStr) {
        final String[] tokens = fileInfoStr.replace("\\t", "\\s").split("\\s+");

        if (tokens.length != 6)
            throw new MegaInvalidResponseException(
                    "The gotten file format is incorrect: Should have 6 tokens"
            );

        try {
            return parseFileInfo(tokens);
        } catch (Exception ex) {
            final MegaInvalidResponseException megaEx =
                    new MegaInvalidResponseException(
                            "Error while parsing file info from %s", fileInfoStr
                    );
            megaEx.addSuppressed(ex);
            throw megaEx;
        }
    }

    private static final FileInfo parseFileInfo(String[] tokens) {
        String filename = parseFileName(tokens);
        final LocalDateTime date = parseDate(tokens);
        final Optional<Long> sizeInBytes = parseSizeInBytes(tokens);
        final Optional<Integer> version = parseVersion(tokens);
        final FileStatus fileStatus = parseFileType(tokens);

        return new FileInfo(filename, date, sizeInBytes, version, fileStatus);
    }

    private static final String parseFileName(String[] tokens) {
        return tokens[5];
    }

    private static final LocalDateTime parseDate(String[] tokens) {
        String dateStr = tokens[3] + " " + tokens[4];
        return MegaUtils.parseFileDate(dateStr);
    }

    private static final Optional<Long> parseSizeInBytes(String[] tokens) {
        try{
            return Optional.of(Long.valueOf(tokens[2]));
        }catch(NumberFormatException ex){
            return Optional.empty();
        }
    }

    private static final Optional<Integer> parseVersion(String[] tokens) {
        try{
            return Optional.of(Integer.valueOf(tokens[1]));
        }catch(NumberFormatException ex){
            return Optional.empty();
        }
    }

    private static final FileStatus parseFileType(String[] tokens) {
        return FileStatus.valueOf(tokens[0]);
    }
}
