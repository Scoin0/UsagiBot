package usagibot.configuration;

import lombok.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Configuration {

    private static File file = new File("config.properties");
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

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
    private String banchoServer;
    private int banchoPort;
    private String banchoChannel;
    private String osuClientId;
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
        log.info("Please set up your configuration file.");
        System.exit(0);
    }

    public void loadConfiguration() throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        FileInputStream fileInput = new FileInputStream(file);
        config.load();
        fileInput.close();

        log.info("Loading Config...");
        if (configVersion != config.getInt("configVersion")) {
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
            osuClientId = config.getString("osu_api_clientid");
            osuAPIKey = config.getString("osu_api_key");
            banchoUsername = config.getString("bancho_username");
            banchoPassword = config.getString("bancho_password");
            banchoServer = config.getString("bancho_server");
            banchoPort = config.getInt("bancho_port");
            banchoChannel = config.getString("bancho_channel");
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
                "#   gosu_url_path = http://127.0.0.1:24050/json\n" +
                "#\n" +
                "# Default is http://127.0.0.1:24050/json\n" +
                "gosu_url_path = http://127.0.0.1:24050/json\n" +
                "\n" +
                "############################################################\n" +
                "# +------------------------------------------------------+ #\n" +
                "# |                    Osu Settings                      | #\n" +
                "# +------------------------------------------------------+ #\n" +
                "############################################################\n" +
                "\n" +
                "# Osu! API key (required)\n" +
                "#\n" +
                "# See https://osu.ppy.sh/home/account/edit and scroll down to obtain an osu! API key\n" +
                "#\n" +
                "# Example:\n" +
                "#   osu_api_clientid = 12345\n" +
                "#   osu_api_key = abc123\n" +
                "osu_api_clientid = \n" +
                "osu_api_key = \n" +
                "\n" +
                "# Bancho API key IRC username, password,  (required)\n" +
                "#\n" +
                "# See https://osu.ppy.sh/p/irc on how to get a Bancho IRC password\n" +
                "#\n" +
                "# Example:\n" +
                "#   bancho_username = UsagiBot\n" +
                "#   bancho_password = abc123\n" +
                "#   bancho_server = irc.ppy.sh\n" +
                "#   bancho_port = 6667\n" +
                "bancho_username = \n" +
                "bancho_password = \n" +
                "bancho_server = irc.ppy.sh\n" +
                "bancho_port = 6667\n" +
                "bancho_channel = #osu";
    }
}
