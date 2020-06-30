package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MegaCmdImport extends AbstractMegaCmdCallerWithParams<ImportInfo> {

    private Optional<String> remotePath = Optional.empty();

    private Optional<String> password = Optional.empty();

    private String exportedLink;

    public MegaCmdImport(String exportedLink) {
        this.exportedLink = exportedLink;
    }

    public MegaCmdImport(String exportedLink, Optional<String> remotePath) {
        this.remotePath = remotePath;
        this.exportedLink = exportedLink;
    }

    public MegaCmdImport(String exportedLink, Optional<String> remotePath, Optional<String> password) {
        this.remotePath = remotePath;
        this.password = password;
        this.exportedLink = exportedLink;
    }

    @Override
    List<String> cmdParams() {
        List<String> cmdFileParams = new LinkedList<>();

        cmdFileParams.add(getExportedLink());

        getPassword().ifPresent(password -> cmdFileParams.add("--password="+password));

        if(getRemotePath().isPresent()) {
            cmdFileParams.add(getRemotePath().get());
        } else {
            cmdFileParams.add("/");
        }

        return cmdFileParams;
    }


    @Override
    public String getCmd() {
        return "import";
    }

    public Optional<String> getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(Optional<String> remotePath) {
        this.remotePath = remotePath;
    }

    public String getExportedLink() {
        return exportedLink;
    }

    public void setExportedLink(String exportedLink) {
        this.exportedLink = exportedLink;
    }

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    @Override
    public ImportInfo call() throws Exception {
        try {
            return MegaUtils.handleCmdWithOutput(executableCommandArray())
                    .stream().findFirst()
                    .map(ImportInfo::parseImportInfo)
                    .orElseThrow(() -> new MegaInvalidResponseException(
                            "Invalid response while exporting '%s'", remotePath
                    ));
        } catch (IOException e) {
            throw new MegaIOException("Error while exporting " + remotePath);
        }
    }
}
