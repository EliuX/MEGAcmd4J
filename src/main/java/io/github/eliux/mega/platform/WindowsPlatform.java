package io.github.eliux.mega.platform;

import java.io.File;
import java.nio.file.Paths;

/**
 * Class that encapsulates Windows specific actions.
 */
public class WindowsPlatform extends OSPlatform {

    @Override
    public String cmdInstruction(String cmd) {
        return String.format("cmd.exe /c MegaClient %s", cmd);
    }

    public String parseLocalPath(String filePath) {
        if(filePath.contains(" ")){
            File file = Paths.get(filePath).toFile();
            return String.format("\"%s\"", file.getAbsolutePath());
        }else{
            return filePath;
        }
    }
}
