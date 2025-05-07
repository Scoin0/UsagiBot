package usagibot.utils;

import usagibot.UsagiBot;
import usagibot.utils.version.GitHubAPI;
import usagibot.utils.version.Version;
import usagibot.utils.version.VersionUtil;

public class Constants {

    public static final String githubURL = "https://api.github.com/repos/scoin0/usagibot/releases";
    public static GitHubAPI gitHubInfo;
    public static Version version;

    public static GitHubAPI getGitHubInfo() {
        if (gitHubInfo == null) {
            gitHubInfo = VersionUtil.getGithubValues();
        }
        return gitHubInfo;
    }

    public static Version getVersion() {
        if (version == null) {
            version = VersionUtil.getCurrentVersion();
        }
        return version;
    }

    public static final String logo =
            "\n _   _                     _ ______         _   \n" +
            "| | | |                   (_)| ___ \\       | |  \n" +
            "| | | | ___   __ _   __ _  _ | |_/ /  ___  | |_ \n" +
            "| | | |/ __| / _` | / _` || || ___ \\ / _ \\ | __|\n" +
            "| |_| |\\__ \\| (_| || (_| || || |_/ /| (_) || |_ \n" +
            " \\___/ |___/ \\__,_| \\__, ||_|\\____/  \\___/  \\__|\n" +
            "                     __/ |                      \n" +
            "                    |___/                       \n" +
            "Version: v" + getVersion() + "\n" +
            "Release Notes:\n" +
            getGitHubInfo().getBody() + "\n";
}
