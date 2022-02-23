package usagibot.configuration;

import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

@Data
@NoArgsConstructor
@Slf4j
public class Configuration {

    private static File file = new File("config.properties");

    // Global Settings
    private String prefix = "!";
    private int configVersion = 1;
    private boolean useUpdater = true;

    // Twitch Settings
    private String twitchUsername;
    private String twitchPassword;
    private String twitchChannel;

    // GOsu Settings
    private String gOsuUrlPath;

    // Osu Settings
    private String banchoUsername;
    private String banchoPassword;
    private String osuAPIKey;
    private String tillerinoAPIKey;

    public void createConfiguration() {

        try {
            if (!file.exists()) {
                log.info("The config file has not been found. Generating one now...");
                file.createNewFile();

                @Cleanup
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(generateConfiguration());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void loadConfiguration() throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        FileInputStream fileInput = new FileInputStream(file);
        config.load();
        fileInput.close();

        log.info("Loading Config...");
        if (configVersion > config.getInt("configVersion")) {
            log.info("There has been an update to the Config file.");
            log.info("Merging old data to new Config file...");
            //TODO: Merge old data to new data
            log.info("Completed. Reloading Config...");
        } else {
            prefix = config.getString("prefix");
            useUpdater = config.getBoolean("update");
            twitchUsername = config.getString("twitch_username");
            twitchPassword = config.getString("twitch_password");
            twitchChannel = config.getString("twitch_channel");
            gOsuUrlPath = config.getString("gosu_url_path");
            osuAPIKey = config.getString("osu_api_key");
            banchoUsername = config.getString("bancho_username");
            banchoPassword = config.getString("bancho_password");
            tillerinoAPIKey = config.getString("tillerino_api_key");
            log.info("Config Loaded.");
        }
    }

    public void initConfiguration() throws Exception {
        if (file.exists()) {
            loadConfiguration();
        } else {
            createConfiguration();
        }
    }

    public static String generateConfiguration() {
        return "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                      Usagibot                        | #\n" +
                "# |                 Configuration File                   | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# DO NOT TOUCH\n" +
                "configVersion = 1\n" +
                "\n" +
                "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                 Settings (Global)                    | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# The prefix for the Twitch bot\n" +
                "# Default is !\n" +
                "prefix = !\n" +
                "\n" +
                "# This program is capable to automatically update finding a new version.\n" +
                "# If true, the program will search for new updates and automatically download them.\n" +
                "# If false, the program will not search for new updates. \n" +
                "# Default is true\n" +
                "update = true\n" +
                "\n" +
                "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                  Twitch Settings                     | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# The Twitch bot IRC username and password (required)\n" +
                "#\n" +
                "# See https://help.twitch.tv/customer/portal/articles/1302780-twitch-irc for\n" +
                "# help obtaining a Twitch IRC token\n" +
                "#\n" +
                "# Example: \n" +
                "#   twitch_username = UsagiBot\n" +
                "#   twitch_password = oauth:abc123\n" +
                "twitch_username =\n" +
                "twitch_password =\n" +
                "\n" +
                "# Twitch channel name (required)\n" +
                "#\n" +
                "# This might be different if you are running the bot on another Twitch account.\n" +
                "# Change this to the channel you are streaming on.\n" +
                "#\n" +
                "# Example:\n" +
                "#   twitch_channel = Scoin0\n" +
                "twitch_channel = \n" +
                "\n" +
                "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                   GOsu Settings                      | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# GOsuMemory websocket path (required if you want the !np command to work)\n" +
                "#\n" +
                "# Inside the config.ini for GOsuMemory, copy the link from serverip and paste as the gosu_url_path.\n" +
                "#\n" +
                "# Example:\n" +
                "#   gosu_url_path = http://127.0.0.1:24050\n" +
                "#\n" +
                "# Default is http://127.0.0.1:24050\n" +
                "gosu_url_path = http://127.0.0.1:24050\n" +
                "\n" +
                "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                    Osu Settings                      | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# Osu! API key (required)\n" +
                "#\n" +
                "# See https://osu.ppy.sh/p/api to obtain an osu! API key\n" +
                "#\n" +
                "# Example: \n" +
                "#   osu_api_key = abc123\n" +
                "osu_api_key = \n" +
                "\n" +
                "# Bancho API key IRC username and password (required)\n" +
                "#\n" +
                "# See https://osu.ppy.sh/p/irc on how to get a Bancho IRC password\n" +
                "#\n" +
                "# Example:\n" +
                "#   bancho_username = UsagiBot\n" +
                "#   bancho_password = abc123\n" +
                "bancho_username = \n" +
                "bancho_password = \n" +
                "\n" +
                "# Tillerino API key (optional)\n" +
                "#\n" +
                "# If you do not have an Tillerino API, leave this section blank.\n" +
                "# PP information will not be displayed if this option is blank.\n" +
                "#\n" +
                "# Example:\n" +
                "#   tillerino_api_key = abc123\n" +
                "tillerino_api_key = ";
    }
}
