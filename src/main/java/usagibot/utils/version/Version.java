package usagibot.utils.version;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Slf4j
public class Version {

    private int majorVersion;
    private int minorVersion;
    private int patchVersion;

    public Version(int majorVersion, int minorVersion, int patchVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.patchVersion = patchVersion;
    }

    public Version(int majorVersion, int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.patchVersion = 0;
    }

    public Version(int majorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = 0;
        this.patchVersion = 0;
    }

    public static Version fromString(String version) {
        String[] section = version.split("\\.");
        if (section.length == 3) {
            return new Version(parseInt(section[0], 1), parseInt(section[1], 0), parseInt(section[2], 0));
        } else if (section.length == 2) {
            return new Version(parseInt(section[0], 1), parseInt(section[1], 0));
        } else if (section.length == 1) {
            return new Version(parseInt(section[0], 1));
        }
        return new Version(1);
    }

    public boolean compareVersion(Version version) {
        if (version == null || this.getMajorVersion() > version.getMajorVersion()) {
            return true;
        } else if (this.getMajorVersion() == version.getMajorVersion()) {
            if (this.getMinorVersion() > version.getMinorVersion()) {
                return true;
            } else if (this.getMinorVersion() == version.getMinorVersion()) {
                if (this.getPatchVersion() > version.getPatchVersion()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int parseInt(String intString, int fallback) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            log.warn("Fallback: " + fallback);
            return fallback;
        }
    }

    @Override
    public String toString() {
        return getMajorVersion() + "." + getMinorVersion() + "." + getPatchVersion();
    }
}