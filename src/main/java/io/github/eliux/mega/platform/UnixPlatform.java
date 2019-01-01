package io.github.eliux.mega.platform;

import java.io.File;
import java.nio.file.Paths;

/**
 * Encapsulate actions related to Unix based OS/Platforms
 */
public class UnixPlatform extends OSPlatform {

    @Override
    public String cmdInstruction(String cmd) {
        return String.format("mega-%s", cmd);
    }

    public String parseLocalPath(String filePath) {
        if(filePath.contains(" ")){
            return filePath.replace(" ", "\\ ");
        }else{
            return filePath;
        }
    }
}
