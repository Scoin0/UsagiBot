package usagibot.twitch.event;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.GameMode;
import usagibot.osu.api.Mods;
import usagibot.osu.api.User;
import usagibot.twitch.TwitchClient;
import usagibot.utils.Utility;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ChatEvent {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();
    private boolean requestToggle = true;
    private Beatmap beatmap;
    private User user = UsagiBot.getClient().getUser(UsagiBot.getConfig().getOsuUsername(), GameMode.OSU);

    /**
     * The ChatEvent
     * @param eventHandler  Twitch4J's Event Handler
     */
    public ChatEvent(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    /**
     * Fires when a chat message is sent in Twitch chat
     * @param event The chat event
     */
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

        // Splits the message into segments
        String[] args = event.getMessage().split(" ");

        // Do not listen to self. Probably not needed, but just in case.
        if (event.getUser().getName().equals("RNRPBot")) return;

        // Display link to command page
        if (event.getMessage().equalsIgnoreCase(prefix + "help")) {
            sendMessage("All Commands: https://github.com/Scoin0/UsagiBot/wiki/Commands");
        }

        // Get the currently playing map
        if (event.getMessage().equalsIgnoreCase(prefix + "np")) {
            log.info("Sending !np command in " + channel);
            try {
                if (Utility.findGosumemory()) {
                    sendMessage(UsagiBot.getConfig().getLocalParsedMessage(UsagiBot.getConfig().getNowPlayingMessage(), beatmap, event.getUser()));
                } else {
                    sendMessage("GOsumemory is not online!");
                    log.warn("GOsumemory is not online!");
                }
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }

        // Change Star Limit
        if (args[0].equalsIgnoreCase(prefix + "starlimit") || args[0].equalsIgnoreCase(prefix + "sl")) {
            if (args.length == 2) {
                try {
                    double newStar = Double.parseDouble(args[1]);
                    if (newStar >= 13.0) {
                        sendMessage("Please choose a number below 13");
                    } else {
                        try {
                            sendMessage("Star Limit changed to: " + newStar + "*");
                            log.info("Star Limit changed to: " + newStar + "*");
                            UsagiBot.getConfig().setOsuStarLimit(newStar);
                        } catch (Exception e) {
                            log.warn(e.getMessage());
                        }
                    }
                } catch (NumberFormatException e) {
                    sendMessage("Please choose a number instead.");
                }
            } else {
                sendMessage("Current Star Limit: " + UsagiBot.getConfig().getOsuStarLimit() +   "*, Usage: !starlimit <num>. For example: !starlimit 6.5");
            }
        }

        // Toggle the requesting feature
        if (event.getMessage().equalsIgnoreCase(prefix + "rq toggle")) {
            if (requestToggle) {
                log.info("Requesting a beatmap has been turned off.");
                sendMessage("Requesting a beatmap has been turned off.");
                requestToggle = false;
            } else {
                log.info("Requesting a beatmap has been turned on.");
                sendMessage("Requesting a beatmap has been turned on.");
                requestToggle = true;
            }
        }

        // Sends streamers osu stats
        if (event.getMessage().equalsIgnoreCase(prefix + "stats")) {
            log.info("Sending !stats command in " + channel);
            sendMessage(userStats());
        }

        // Receive, parse, and send beatmap to Osu! and Twitch Chat
        if (event.getMessage().contains("https://osu.ppy.sh/")) {
            if (requestToggle) {
                log.info("Received possible osu song request. Parsing now...");
                beatmap = UsagiBot.getClient().getBeatmap(parseMessage(event.getMessage()));
                log.info("Beatmap ID Found: " + beatmap.getId());
                if (event.getMessage().contains("+")) {
                    String[] m = event.getMessage().split("\\+");
                    String m1 = m[1];
                    if (beatmap.getDifficulty_rating() > UsagiBot.getConfig().getOsuStarLimit()) {
                        sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuStarLimitMessage(), beatmap, event.getUser()));
                    } else {
                        sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getTwitchMessage() + " +" + Mods.getMods(Mods.fromShortNamesContinuous(m1)), beatmap, event.getUser()));
                        sendIRCMessage(event.getUser(), beatmap, " +" + Mods.getMods(Mods.fromShortNamesContinuous(m1)));
                    }
                }
            } else {
                sendMessage("You cannot request a beatmap at this time.");
            }
        }
    }

    /**
     * Sends a message within Twitch chat
     * @param message   The message to be sent
     */
    public void sendMessage(String message) {
        TwitchClient.client.getChat().sendMessage(channel, message);
    }

    /**
     * Sends a message within Osu
     * @param user      The user that sent the beatmap
     * @param beatmap   The beatmap information
     */
    public void sendIRCMessage(EventUser user, Beatmap beatmap, String message) {
        UsagiBot.getIrcBot().getUserChannelDao().getUser(UsagiBot.getConfig().getBanchoUsername()).send().message(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuIrcMessage(), beatmap, user) + message);
    }

    /**
     * Parses the beatmap link sent in Twitch
     * @param message   The beatmap link that was sent
     * @return          The beatmap url
     */
    public String parseMessage(String message) {
        String delimiters = "https?:\\/\\/osu.ppy.sh\\/(beatmapsets)\\/([0-9]*)(#osu|#taiko|#ctb|#maina)\\/([0-9]*)";
        Pattern urlPattern = Pattern.compile(delimiters, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = urlPattern.matcher(message);
        List<String> urlSplit = new ArrayList<String>();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                urlSplit.add(matcher.group(i));
            }
        }

        switch (urlSplit.size()) {
            case 4:
                String beatmap = urlSplit.get(3);
                return beatmap;
            default:
                log.warn("Invalid beatmap link sent.");
        }
        return null;
    }

    /**
     * Sends some user stats
     * @return  The user stats
     */
    private String userStats() {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        return "Stats for " + user.getUsername() + ": "
                + " PP: " + nf.format(user.getStatistics().getPp())
                + " Global Rank: #" + nf.format(user.getStatistics().getGlobal_rank())
                + " Level: " + user.getStatistics().getLevel().getCurrent()
                + " Accuracy: " + user.getStatistics().getHit_accuracy() + "%"
                + " Total Score: " + nf.format(user.getStatistics().getRanked_score())
                + " Total Max Combo: " + nf.format(user.getStatistics().getMaximum_combo()) + "x"
                + " Play Count: " + nf.format(user.getStatistics().getPlay_count());
    }
}