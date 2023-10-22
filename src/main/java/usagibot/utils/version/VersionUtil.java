package usagibot.utils.version;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import usagibot.UsagiBot;
import usagibot.utils.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

@Slf4j
public class VersionUtil {

    private static String version;

    /**
     * Gets the current version from the build.gradle
     * @return  The current version
     */
    public static Version getCurrentVersion() {
        if (version == null) {
            version = "1.0.0";
            }
        version = UsagiBot.class.getPackage().getImplementationVersion();
        return Version.fromString(version);
    }

    /**
     * Gets the latest version off of Github Releases
     * @return  The latest version off of Github Releases
     */
    public static Version getLatestVersion() {
        version = Constants.gitHubInfo.getTag_name();
        return Version.fromString(version);
    }

    /**
     * Connects to the internet to retrieve the latest version
     * @return The latest version on Github
     */
    public static GitHubAPI getGithubValues() {
        GitHubAPI gitHubAPI = new GitHubAPI();

        try {
            URL githubURL = new URL(Constants.githubURL);

            try (InputStream inputStream = githubURL.openStream()) {
                JSONArray json = new JSONArray(IOUtils.toString(inputStream, Charset.forName("UTF-8")));
                String responseBody = json.get(0).toString();
                gitHubAPI = new JsonMapper().readValue(responseBody, GitHubAPI.class);
            } catch (IOException e) {
                log.warn("Failed to read GitHub's API response: " + e.getMessage());
            }

        } catch (MalformedURLException e) {
            log.warn("Invalid GitHub URL: " + e.getMessage());
        }
        return gitHubAPI;
    }
}