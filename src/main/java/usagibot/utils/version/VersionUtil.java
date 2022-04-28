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
            version = "1.0.0";
            }
        version = UsagiBot.class.getPackage().getImplementationVersion();
        return Version.fromString(version);
    }

    public static Version getLatestVersion() {
        version = getGithubValues().getTag_name();
        return Version.fromString(version);
    }

    public static GitHubAPI getGithubValues() {
        GitHubAPI github = new GitHubAPI();
        try {
            JSONArray json = new JSONArray(IOUtils.toString(new URL(Constants.githubURL), Charset.forName("UTF-8")));
            String responseBody = json.get(0).toString();
            github = new JsonMapper().readValue(responseBody, GitHubAPI.class);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return github;
    }
}