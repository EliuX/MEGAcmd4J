package io.github.eliux.mega.cmd.cmd;

import com.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaInvalidResponseException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MegaCmdExport extends AbstractMegaCmdCallerWithParams<ExportInfo> {

    private final Optional<String> remotePath;

    private boolean exportDeleted;

    private boolean listOnly;

    public MegaCmdExport(String remotePath) {
        this.remotePath = Optional.of(remotePath);
    }

    @Override
    String cmdParams() {
        final StringBuilder cmdParamsBuilder = new StringBuilder();

        remotePath
                .filter(x -> !listOnly)
                .ifPresent(x -> cmdParamsBuilder
                        .append(exportDeleted ? "-d " : "-a ")
                        .append("-f ")
                );

        remotePath.ifPresent(cmdParamsBuilder::append);

        return cmdParamsBuilder.toString();
    }

    @Override
    public String getCmd() {
        return "export";
    }

    @Override
    public ExportInfo call() {
        try {
            return MegaUtils.execCmdWithOutput(executableCommand())
                    .stream().findFirst()
                    .map(ExportInfo::parseExportInfo)
                    .orElseThrow(() -> new MegaInvalidResponseException(
                            "Invalid response while exporting '%s'", remotePath
                    ));
        } catch (IOException e) {
            throw new MegaIOException("Error while exporting " + remotePath);
        }
    }

    public List<ExportInfo> list() {
        justList();
        try {
            return MegaUtils.execCmdWithOutput(executableCommand()).stream()
                    .map((ExportInfo::parseExportListInfo))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new MegaIOException("Error while exporting " + remotePath);
        }
    }

    public MegaCmdExport enablePublicLink() {
        exportDeleted = false;
        return this;
    }

    public MegaCmdExport removePublicLink() {
        exportDeleted = true;
        return this;
    }

    public boolean isExportDeleted() {
        return exportDeleted;
    }

    protected MegaCmdExport justList() {
        listOnly = true;
        return this;
    }
}
