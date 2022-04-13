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
        return """
                ############################################################\s
                # +------------------------------------------------------+ #
                # |                      Usagibot                        | #
                # |                 Configuration File                   | #
                # +------------------------------------------------------+ #
                ############################################################

                # DO NOT TOUCH
                configVersion = 1

                ############################################################
                # +------------------------------------------------------+ #
                # |                 Settings (Global)                    | #
                # +------------------------------------------------------+ #
                ############################################################

                # The prefix for the Twitch bot
                # Default is !
                prefix = !

                # This program is capable to automatically update finding a new version.
                # If true, the program will search for new updates and automatically download them.
                # If false, the program will not search for new updates.\s
                # Default is true
                update = true

                ############################################################
                # +------------------------------------------------------+ #
                # |                  Twitch Settings                     | #
                # +------------------------------------------------------+ #
                ############################################################

                # The Twitch bot IRC username and password (required)
                #
                # See https://help.twitch.tv/customer/portal/articles/1302780-twitch-irc for
                # help obtaining a Twitch IRC token
                #
                # Example:\s
                #   twitch_username = UsagiBot
                #   twitch_password = abc123 (without the 'oauth:')
                twitch_username =
                twitch_password =

                # Twitch channel name (required)
                #
                # This might be different if you are running the bot on another Twitch account.
                # Change this to the channel you are streaming on.
                #
                # Example:
                #   twitch_channel = Scoin0
                twitch_channel =\s

                ############################################################
                # +------------------------------------------------------+ #
                # |                   GOsu Settings                      | #
                # +------------------------------------------------------+ #
                ############################################################

                # GOsuMemory websocket path (required if you want the !np command to work)
                #
                # Inside the config.ini for GOsuMemory, copy the link from serverip and paste as the gosu_url_path.
                #
                # Example:
                #   gosu_url_path = http://127.0.0.1:24050
                #
                # Default is http://127.0.0.1:24050
                gosu_url_path = http://127.0.0.1:24050

                ############################################################
                # +------------------------------------------------------+ #
                # |                    Osu Settings                      | #
                # +------------------------------------------------------+ #
                ############################################################

                # Osu! API key (required)
                #
                # See https://osu.ppy.sh/p/api to obtain an osu! API key
                #
                # Example:\s
                #   osu_api_key = abc123
                osu_api_clientid =
                osu_api_key =\s

                # Bancho API key IRC username and password (required)
                #
                # See https://osu.ppy.sh/p/irc on how to get a Bancho IRC password
                #
                # Example:
                #   bancho_username = UsagiBot
                #   bancho_password = abc123
                bancho_username =\s
                bancho_password =\s

                # Tillerino API key (optional)
                #
                # If you do not have an Tillerino API, leave this section blank.
                # PP information will not be displayed if this option is blank.
                #
                # Example:
                #   tillerino_api_key = abc123
                tillerino_api_key =\s""";
    }
}
