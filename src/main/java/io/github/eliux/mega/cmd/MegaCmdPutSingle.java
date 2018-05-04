package io.github.eliux.mega.cmd;

public class MegaCmdPutSingle extends AbstractMegaCmdPut {

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
