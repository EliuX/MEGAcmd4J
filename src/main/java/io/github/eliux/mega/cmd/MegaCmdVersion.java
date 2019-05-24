package io.github.eliux.mega.cmd;

import io.github.eliux.mega.MegaUtils;
import io.github.eliux.mega.error.MegaInvalidResponseException;
import io.github.eliux.mega.error.MegaUnexpectedFailureException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MegaCmdVersion extends
        AbstractMegaCmdCallerWithParams<MegaCmdVersion.MegaCmdVersionResponse> {

    private boolean isExtendedInfoShown;

    public MegaCmdVersion() {
        this.isExtendedInfoShown = false;
    }

    public MegaCmdVersion(boolean isExtendedInfoShown) {
        this.isExtendedInfoShown = isExtendedInfoShown;
    }

    public boolean isExtendedInfoShown() {
        return isExtendedInfoShown;
    }

    MegaCmdVersion showExtendedInfo() {
        this.isExtendedInfoShown = true;
        return this;
    }

    MegaCmdVersion hideExtendedInfo() {
        this.isExtendedInfoShown = false;
        return this;
    }

    @Override
    public String getCmd() {
        return "version";
    }

    @Override
    List<String> cmdParams() {
        return isExtendedInfoShown ? Collections.singletonList("-l")
                : Collections.emptyList();
    }

    @Override
    public MegaCmdVersionResponse call() {
        try {
            final List<String> versionDataLines =
                    MegaUtils.handleCmdWithOutput(executableCommandArray());
            MegaCmdVersionResponse version = parseCmdVersion(versionDataLines.get(0));

            if (isExtendedInfoShown) {
                final String sdkVersion = parseSDKVersion(versionDataLines.get(1));
                final String sdkCredits = parseSDKCredits(versionDataLines.get(2));
                final String sdkLicense = parseSDKCredits(versionDataLines.get(3));
                final String license = parseSDKCredits(versionDataLines.get(4));
                final List<String> features = parseFeaturesEnabled(versionDataLines);

                return new MegaCmdVersionExtendedResponse(
                        version, sdkVersion, sdkCredits, sdkLicense, license, features
                );
            }

            return version;
        } catch (Exception e) {
            throw new MegaUnexpectedFailureException();
        }
    }

    protected static final MegaCmdVersionResponse parseCmdVersion(
            String versionResponse
    ) {
        final Matcher versionMatcher = Pattern.compile(
                "(.+) version: (?<version>.+): code (?<versionCode>.+)"
        ).matcher(versionResponse);

        if (versionMatcher.find()) {
            return new MegaCmdVersionResponse(
                    versionMatcher.group("version"),
                    versionMatcher.group("versionCode")
            );
        } else {
            throw new MegaInvalidResponseException("Unexpected MEGAcmd version");
        }
    }

    protected static final String parseSDKVersion(String sdkVersionResponse) {
        return sdkVersionResponse.split(": ")[1];
    }

    protected static final String parseSDKCredits(String sdkCreditsResponse) {
        return sdkCreditsResponse.split(": ")[1];
    }

    protected static final String parseSDKLicense(String sdkLicenseResponse) {
        return sdkLicenseResponse.split(": ")[1];
    }

    protected static final String parseLicense(String licenseResponse) {
        return licenseResponse.split(": ")[1];
    }

    protected static List<String> parseFeaturesEnabled(List<String> versionDataLines) {
        final int featuresHeader = versionDataLines.indexOf("Features enabled:");
        final int featuresOffset = featuresHeader + 1;
        if (versionDataLines.size() > featuresOffset) {
            return versionDataLines.stream()
                    .skip(featuresOffset)
                    .map(x -> x.substring(2))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    protected static class MegaCmdVersionResponse {

        private final String version;

        private final String versionCode;

        protected MegaCmdVersionResponse(MegaCmdVersionResponse version) {
            this.version = version.getVersion();
            this.versionCode = version.getVersionCode();
        }

        protected MegaCmdVersionResponse(String version, String versionCode) {
            this.version = version;
            this.versionCode = versionCode;
        }

        public String getVersion() {
            return version;
        }

        public String getVersionCode() {
            return versionCode;
        }
    }

    protected static class MegaCmdVersionExtendedResponse extends MegaCmdVersionResponse {

        private final String sdkVersion;
        private final String sdkCredits;
        private final String sdkLicense;
        private final String license;
        private final List<String> features;

        protected MegaCmdVersionExtendedResponse(
                MegaCmdVersionResponse version,
                String sdkVersion, String sdkCredits, String sdkLicense,
                String license, List<String> features
        ) {
            super(version);
            this.sdkVersion = sdkVersion;
            this.sdkCredits = sdkCredits;
            this.sdkLicense = sdkLicense;
            this.license = license;
            this.features = features;
        }

        public String getSdkVersion() {
            return sdkVersion;
        }

        public String getSdkCredits() {
            return sdkCredits;
        }

        public String getSdkLicense() {
            return sdkLicense;
        }

        public String getLicense() {
            return license;
        }

        public List<String> getFeatures() {
            return features;
        }
    }
}
