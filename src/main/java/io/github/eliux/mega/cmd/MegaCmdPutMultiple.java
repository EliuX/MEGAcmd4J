package io.github.eliux.mega.cmd;

import io.github.eliux.mega.error.MegaCmdInvalidArguments;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MegaCmdPutMultiple extends MegaCmdPut{

    private String remotePath;

    private List<String> localFiles;

    public MegaCmdPutMultiple(String remotePath, String... localFiles) {
        this.remotePath = remotePath;
        this.localFiles = Arrays.asList(localFiles);
    }

    public MegaCmdPutMultiple setRemotePath(String remotePath) {
        if(remotePath!= null){
            this.remotePath = remotePath;
        }
        return this;
    }

    public String getRemotePath() {
        return remotePath;
    }

    private String cmdLocalFilesParams(){
        if(localFiles == null || localFiles.isEmpty()){
            throw new MegaCmdInvalidArguments("There are not local files specified!");
        }

        return localFiles.stream().collect(Collectors.joining(" "));
    }

    @Override
    protected String cmdFileParams() {
        return cmdLocalFilesParams() + " " + getRemotePath();
    }
}
