package io.github.eliux.mega.cmd;

import java.util.Optional;

public abstract class MegaCmdPut extends MegaCmdWithParams {

    private boolean createRemoteIdDoesntExist;

    private boolean waitForItToEnd;

    private boolean ignoreQuotaSurpassingWarning;

    @Override
    String cmdParams() {
        StringBuilder cmdParamsBuilder = new StringBuilder();

        if(createRemoteIdDoesntExist){
            cmdParamsBuilder.append("-c ");
        }

        if(waitForItToEnd){
            cmdParamsBuilder.append("-q ");
        }

        if(ignoreQuotaSurpassingWarning){
            cmdParamsBuilder.append("--ignore-quota-warn ");
        }

        cmdParamsBuilder.append(cmdFileParams());

        return cmdParamsBuilder.toString();
    }

    public String getCmd() {
        return getPlatform().cmdInstruction("mega-put");
    }

    @Override
    protected Optional executeSysCmd(String cmdStr) {
        return Optional.empty();
    }

    public MegaCmdPut createRemoteIfDoesntExist() {
        createRemoteIdDoesntExist = true;
        return this;
    }

    public MegaCmdPut dontCreateRemoteIfDoesntExist() {
        createRemoteIdDoesntExist = false;
        return this;
    }

    public boolean isCreateRemoteIdDoesntExist() {
        return createRemoteIdDoesntExist;
    }

    public MegaCmdPut waitForItToEnd(){
        waitForItToEnd = true;
        return this;
    }

    public MegaCmdPut dontWaitForItToEnd(){
        waitForItToEnd = false;
        return this;
    }

    public boolean isWaitForItToEnd() {
        return waitForItToEnd;
    }

    public MegaCmdPut ignoreQuotaSurpassingWarning() {
        this.ignoreQuotaSurpassingWarning = true;
        return this;
    }

    public MegaCmdPut dontIgnoreQuotaSurpassingWarning() {
        this.ignoreQuotaSurpassingWarning = false;
        return this;
    }

    public boolean isIgnoreQuotaSurpassingWarning() {
        return ignoreQuotaSurpassingWarning;
    }

    protected abstract String cmdFileParams();
}
