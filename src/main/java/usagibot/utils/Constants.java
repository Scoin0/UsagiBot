package usagibot.utils;

import usagibot.utils.version.GitHubAPI;
import usagibot.utils.version.Version;
import usagibot.utils.version.VersionUtil;

public class Constants {

    public static final String githubURL = "https://api.github.com/repos/scoin0/usagibot/releases";
    public static final GitHubAPI gitHubInfo = VersionUtil.getGithubValues();
    public static final Version version = VersionUtil.getCurrentVersion();
    public static final String logo =
            "\n _   _                     _ ______         _   \n" +
            "| | | |                   (_)| ___ \\       | |  \n" +
            "| | | | ___   __ _   __ _  _ | |_/ /  ___  | |_ \n" +
            "| | | |/ __| / _` | / _` || || ___ \\ / _ \\ | __|\n" +
            "| |_| |\\__ \\| (_| || (_| || || |_/ /| (_) || |_ \n" +
            " \\___/ |___/ \\__,_| \\__, ||_|\\____/  \\___/  \\__|\n" +
            "                     __/ |                      \n" +
            "                    |___/                       \n" +
            "Version: v" + version + "\n" +
            "Release Notes:\n" +
            gitHubInfo.getBody() + "\n";
}
