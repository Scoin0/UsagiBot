package usagibot.configuration;

import com.github.twitch4j.common.events.domain.EventUser;
import lombok.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.BeatmapAttributes;
import usagibot.utils.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class Configuration {

    private static File file = new File("config.properties");
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    // Global Settings
    private String prefix = "!";
    private int configVersion = 5;
    private boolean useUpdater = true;

    // Twitch Settings
    private String twitchUsername = "";
    private String twitchPassword = "";
    private String twitchChannel = "";

    // GOsu Settings
    private String gOsuUrlPath = "http://127.0.0.1:24050/json";

    // Osu Settings
    private String banchoUsername = "";
    private String osuUsername = "";
    private String banchoPassword = "";
    private String banchoServer = "irc.ppy.sh";
    private int banchoPort = 6667;
    private String banchoChannel = "#osu";
    private String osuClientId = "";
    private String osuAPIKey = "";
    private double osuStarLimit = 6.0;

    // Custom Messages
    private String twitchMessage = "[RECEIVED] > <user_sent> [<ranked_status>] <artist> - <title> [<version>] <music_note_emoji> <length> <star_emoji> <star_rating> BPM:<bpm> AR:<ar> OD:<od>";
    private String osuIrcMessage = "[<user_sent>] > [https://osu.ppy.sh/b/<beatmap_id> <artist> - <title> [<version>]] <music_note_emoji> <length> <star_emoji> <star_rating> BPM:<bpm> AR:<ar> OD:<od>";
    private String nowPlayingMessage = "Here you go! <beatmap_url>";
    private String osuStarLimitMessage = "<red_exclamation> Sorry The star level exceeds the limit.";

    /**
     * Check to see if a config file has been created. If not, generate one.
     */
    public void createConfiguration() {

        try {
            if (!file.exists()) {
                log.info("The config file has not been found. Generating one now...");
                file.createNewFile();
                generateConfiguration();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Load all the configuration values. If there is an update to the config then it will load all previous
     * values first before adding the new values.
     * @throws Exception    If it cannot save or load the file.
     */
    public void loadConfiguration() throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        FileInputStream fileInput = new FileInputStream(file);
        fileInput.close();

        // Load the configuration first
        log.info("Loading Config...");
        prefix = config.getString("prefix");
        useUpdater = config.getBoolean("update");
        twitchUsername = config.getString("twitch_username");
        twitchPassword = config.getString("twitch_password");
        twitchChannel = config.getString("twitch_channel");
        gOsuUrlPath = config.getString("gosu_url_path");
        osuClientId = config.getString("osu_api_clientid");
        osuAPIKey = config.getString("osu_api_key");
        banchoUsername = config.getString("bancho_username");
        osuUsername = config.getString("osu_username");
        banchoPassword = config.getString("bancho_password");
        banchoServer = config.getString("bancho_server");
        banchoPort = config.getInt("bancho_port");
        banchoChannel = config.getString("bancho_channel");
        twitchMessage = config.getString("received_message");
        nowPlayingMessage = config.getString("np_message");
        osuStarLimit = config.getFloat("osu_star_limit");
        osuStarLimitMessage = config.getString("osu_star_limit_message");

        // Check to see if the config version is different from my version.
        if (configVersion != config.getInt("configVersion")) {
            log.info("There has been an update to the Config file.");
            log.info("Adding new values...");
            file.delete();
            generateConfiguration();
            log.info("Completed. Reloading Config...");
            initConfiguration();
        } else {
            // This section is for adding new or updated lines to the config file.
            osuIrcMessage = config.getString("osu_message");
            log.info("Config Loaded.");
        }
    }

    /**
     * Initializes the configuration
     * @throws Exception    If it cannot load or save the file
     */
    public void initConfiguration() throws Exception {
        if (file.exists()) {
            loadConfiguration();
        } else {
            createConfiguration();
        }
    }

    /**
     * Sets the star limit
     * @param starLimit     The star limit
     * @throws Exception    If it cannot save the file
     */
    public void setOsuStarLimit(double starLimit) throws Exception {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        this.osuStarLimit = starLimit;
        config.setProperty("osu_star_limit", starLimit);
        config.save();
    }

    /**
     * Get the Osu API message and parse it, mostly for any Osu API command.
     * @param message   The message to be parsed
     * @param beatmap   The beatmap information
     * @param user      The user who sent the message on Twitch
     * @return          The parsed and compiled message
     */
    public String getAPIParsedMessage(String message, Beatmap beatmap, EventUser user) {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes(String.valueOf(beatmap.getId()));
        Map<String, Object> keywords = new HashMap<>();
        keywords.put("music_note_emoji", "\u266B");
        keywords.put("star_emoji", "\u2605");
        keywords.put("red_exclamation", "\u2757");
        keywords.put("ranked_status", beatmap.getStatus());
        keywords.put("artist", beatmap.getBeatmapset().getArtist());
        keywords.put("title", beatmap.getBeatmapset().getTitle());
        keywords.put("version", beatmap.getVersion());
        keywords.put("length", Utility.convertTime(beatmap.getTotal_length()));
        keywords.put("star_rating", beatmap.getDifficulty_rating());
        keywords.put("beatmap_id", beatmap.getId());
        keywords.put("beatmap_url", beatmap.getUrl());
        keywords.put("bpm", beatmap.getBpm());
        keywords.put("ar", beatmap.getAr());
        keywords.put("od", map.getAttributes().getOverall_difficulty());
        keywords.put("user_sent", user.getName());
        keywords.put("star_rating_limit", osuStarLimit);
        return parseKeywords(message, keywords);
    }

    /**
     * Get the gosumemory message and parse it, mostly for the Now Playing command.
     * @param message   The message to be parsed
     * @param beatmap   The beatmap information
     * @param user      The user who sent the Now Playing command
     * @return          The parsed and complied message
     * @throws IOException  If it cannot find the gosumemory file
     */
    public String getLocalParsedMessage(String message, Beatmap beatmap, EventUser user) throws IOException {
        BeatmapAttributes map = UsagiBot.getClient().getBeatmapAttributes(String.valueOf(Utility.getSongFromGosuMemory().getId()));
        Map<String, Object> keywords = new HashMap<>();
        keywords.put("music_note_emoji", "\u266B");
        keywords.put("star_emoji", "\u2605");
        keywords.put("red_exclamation", "\u2757");
        keywords.put("ranked_status", Utility.getSongFromGosuMemory().getStatus());
        keywords.put("artist", Utility.getSongFromGosuMemory().getBeatmapset().getArtist());
        keywords.put("title", Utility.getSongFromGosuMemory().getBeatmapset().getTitle());
        keywords.put("version", Utility.getSongFromGosuMemory().getVersion());
        keywords.put("length", Utility.convertTime(Utility.getSongFromGosuMemory().getTotal_length()));
        keywords.put("star_rating", Utility.getSongFromGosuMemory().getDifficulty_rating());
        keywords.put("beatmap_id", Utility.getSongFromGosuMemory().getBeatmapset_id());
        keywords.put("beatmap_url", Utility.getSongFromGosuMemory().getUrl());
        keywords.put("bpm", Utility.getSongFromGosuMemory().getBpm());
        keywords.put("ar", Utility.getSongFromGosuMemory().getAr());
        keywords.put("od", map.getAttributes().getOverall_difficulty());
        keywords.put("user_sent", user.getName());
        keywords.put("star_rating_limit", osuStarLimit);
        return parseKeywords(message, keywords);
    }

    /**
     * Parse the keywords used within the configuration
     * @param message   The message to be parsed
     * @param keywords  The keywords to be added to the message
     * @return          The parsed and compiled message
     */
    public String parseKeywords(String message, Map<String, Object> keywords) {
        StringBuilder formatter = new StringBuilder(message);
        List<Object> keywordsList = new ArrayList<>();

        Matcher matcher = Pattern.compile("\\<(\\w+)>").matcher(message);

        while (matcher.find()) {
            String key = matcher.group(1);
            String formatKey = String.format("<%s>", key);
            int index = formatter.indexOf(formatKey);

            if (index != -1) {
                formatter.replace(index, index + formatKey.length(), "%s");
                keywordsList.add(keywords.get(key));
            }
        }
        return String.format(formatter.toString(), keywordsList.toArray());
    }

    /**
     * Generate all configuration with its default or set values.
     * @throws ConfigurationException   Occurs if an object cannot be initialized
     */
    public void generateConfiguration() throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration(file);
        config.clear();
        config.setHeader("Welcome to the Configuration File!\nPlease look at https://github.com/Scoin0/UsagiBot/wiki/Configuration to understand how to get all the api keys.");
        config.addProperty("configVersion", configVersion);
        config.addProperty("prefix", prefix);
        config.addProperty("update", useUpdater);
        config.addProperty("twitch_username", twitchUsername);
        config.addProperty("twitch_password", twitchPassword);
        config.addProperty("twitch_channel", twitchChannel);
        config.addProperty("gosu_url_path", gOsuUrlPath);
        config.addProperty("osu_api_clientid", osuClientId);
        config.addProperty("osu_api_key", osuAPIKey);
        config.addProperty("bancho_username", banchoUsername);
        config.addProperty("osu_username", osuUsername);
        config.addProperty("osu_star_limit", osuStarLimit);
        config.addProperty("bancho_password", banchoPassword);
        config.addProperty("bancho_server", banchoServer);
        config.addProperty("bancho_port", banchoPort);
        config.addProperty("bancho_channel", banchoChannel);
        config.addProperty("received_message", twitchMessage);
        config.addProperty("osu_message", osuIrcMessage);
        config.addProperty("np_message", nowPlayingMessage);
        config.addProperty("osu_star_limit_message", osuStarLimitMessage);
        config.save(file);
    }

}
