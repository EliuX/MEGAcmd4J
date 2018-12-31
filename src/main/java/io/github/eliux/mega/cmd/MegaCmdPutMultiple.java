package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaCmdInvalidArguments;
import io.github.eliux.mega.platform.OSPlatform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MegaCmdPutMultiple extends AbstractMegaCmdPut {

    private String remotePath;

    private List<String> localFiles;

    public MegaCmdPutMultiple(String remotePath, String... localFiles) {
        this.remotePath = remotePath;
        this.localFiles = new ArrayList<>(Arrays.asList(localFiles));
    }

    public String getRemotePath() {
        return remotePath;
    }

    public MegaCmdPutMultiple setRemotePath(String remotePath) {
        if (remotePath != null) {
            this.remotePath = remotePath;
        }
        return this;
    }

    public List<String> localFiles() {
        return Collections.unmodifiableList(localFiles);
    }

    private String cmdLocalFilesParams() {
        if (localFiles == null || localFiles.isEmpty()) {
            throw new MegaCmdInvalidArguments(
                    "There are not local files specified!"
            );
        }

        return localFiles.stream()
                .map(OSPlatform.getCurrent()::parseLocalPath)
                .collect(Collectors.joining(" "));
    }

    @Override
    protected String cmdFileParams() {
        final String remotePath = MegaUtils.parseRemotePath(getRemotePath());
        return cmdLocalFilesParams() + " " + remotePath;
    }

    public void addLocalFileToUpload(String filename) {
        localFiles.add(filename);
    }
}
