package usagibot.utils.version;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import usagibot.UsagiBot;
import usagibot.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@Slf4j
public class VersionUtil {

    private static String version;

    public static Version getCurrentVersion() {
        if (version == null) {
            version = "1";
            }
        version = UsagiBot.class.getPackage().getImplementationVersion();
        return Version.fromString(version);
    }

    public static Version getLatestVersion() {
        try {
            JSONArray json = new JSONArray(IOUtils.toString(new URL(Constants.githubURL), Charset.forName("UTF-8")));
            String responseBody = json.get(0).toString();
            GitHubAPI github = new JsonMapper().readValue(responseBody, GitHubAPI.class);
            version = github.getTag_name();
        } catch (IOException e) {
            log.info("Unable to find URL");
        }
        return Version.fromString(version);
    }
}