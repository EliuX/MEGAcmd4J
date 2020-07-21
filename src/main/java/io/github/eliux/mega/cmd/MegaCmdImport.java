package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MegaCmdImport extends AbstractMegaCmdCallerWithParams<ImportInfo> {

    private String remotePath;

    private String password;

    private String exportedLink;

    public MegaCmdImport(String exportedLink) {
        this.exportedLink = exportedLink;
    }

    public MegaCmdImport setRemotePath(String remotePath) {
        this.remotePath = remotePath;
        return  this;
    }

    public MegaCmdImport setPassword(String password) {
        this.password = password;
        return  this;
    }

    @Override
    List<String> cmdParams() {
        List<String> cmdFileParams = new LinkedList<>();

        cmdFileParams.add(getExportedLink());

        if(password != null) {
            cmdFileParams.add("--password="+password);
        }

        if(remotePath != null) {
            cmdFileParams.add(remotePath);
        } else {
            cmdFileParams.add("/");
        }

        return cmdFileParams;
    }


    @Override
    public String getCmd() {
        return "import";
    }


    public String getExportedLink() {
        return exportedLink;
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
