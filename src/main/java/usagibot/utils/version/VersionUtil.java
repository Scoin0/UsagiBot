package usagibot.utils.version;

import lombok.extern.slf4j.Slf4j;
import ru.dmerkushov.httphelper.HttpHelper;
import ru.dmerkushov.httphelper.HttpHelperException;
import usagibot.UsagiBot;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class VersionUtil {

    private static final Pattern versionPattern = Pattern.compile("v([0-9]+.[0-9]+.[0-9]+)");
    private static String version;

    public static Version getCurrentVersion() {
        if (version == null) {
            version = "1";
            }
        version = UsagiBot.class.getPackage().getImplementationVersion();
        return Version.fromString(version); //Change this
    }

    public static Version getLatestVersion() {
        try {
            String request = HttpHelper.getTextHttpDocument("UNKOWN");
            Matcher matcher = versionPattern.matcher(request);
            if (matcher.find()) {
                return Version.fromString(matcher.group(1));
            }
        } catch (HttpHelperException e) {
            log.info("Unable to find URL");
        }
        return Version.fromString("1");
    }
}