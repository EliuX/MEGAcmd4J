package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaWrongArgumentsException;
import java.util.*;

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
        return localFiles;
    }

    private List<String> cmdLocalFilesParams() {
        if (localFiles == null || localFiles.isEmpty()) {
            throw new MegaWrongArgumentsException(
                    "There are not local files specified!"
            );
        }

        return Collections.unmodifiableList(localFiles);
    }

    @Override
    protected List<String> cmdFileParams() {
        List<String> result = new LinkedList<>(localFiles);

        result.add(getRemotePath());

        return result;
    }

    public void addLocalFileToUpload(String filename) {
        localFiles.add(filename);
    }
}
