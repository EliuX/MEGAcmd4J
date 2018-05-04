package io.github.eliux.mega.cmd;

public class MegaCmdPutSingle extends MegaCmdPut{

    private final String localFile;

    public MegaCmdPutSingle(String localFile){
        this.localFile = localFile;
    }

    protected String cmdFileParams(){
        return localFile;

    }

    public String getLocalFile() {
        return localFile;
    }
}
