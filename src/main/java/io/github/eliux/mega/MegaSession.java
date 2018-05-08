package io.github.eliux.mega;

import io.github.eliux.mega.auth.MegaAuth;
import io.github.eliux.mega.cmd.*;

public class MegaSession {

    private MegaAuth authentication;

    public MegaSession(MegaAuth authentication) {
        this.authentication = authentication;
    }

    public MegaAuth getAuthentication() {
        return authentication;
    }

    public void logout() {
        new MegaCmdLogout().run();
    }

    public String sessionID() {
        return new MegaCmdSession().call();
    }

    public String whoAmI() {
        return new MegaCmdWhoAmI().call();
    }

    public MegaCmdPutSingle uploadFile(String localFilePath) {
        return new MegaCmdPutSingle(localFilePath);
    }

    public MegaCmdPutSingle uploadFile(String localFilePath, String remotePath) {
        return new MegaCmdPutSingle(localFilePath, remotePath);
    }

    public MegaCmdPutMultiple uploadFiles(String remotePath, String... filenames) {
        return new MegaCmdPutMultiple(remotePath, filenames);
    }

    public MegaCmdMakeDirectory makeDirectory(String remotePath) {
        return new MegaCmdMakeDirectory(remotePath);
    }

    public MegaCmdCopy copy(String remoteSourcePath, String remoteTarget) {
        return new MegaCmdCopy(remoteSourcePath, remoteTarget);
    }

    public MegaCmdMove move(String remoteSourcePath, String remoteTarget) {
        return new MegaCmdMove(remoteSourcePath, remoteTarget);
    }

    public MegaCmdList ls(String remotePath){
        return new MegaCmdList(remotePath);
    }

    public MegaCmdGet get(String remotePath){
        return new MegaCmdGet(remotePath);
    }

    public MegaCmdGet get(String remotePath, String localPath){
        return new MegaCmdGet(remotePath, localPath);
    }
}
