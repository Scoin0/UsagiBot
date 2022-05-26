package usagibot.twitch.event;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.objects.Beatmap;
import usagibot.twitch.TwitchClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ChatEvent {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();
    private boolean requestToggle = true;
    private Beatmap beatmap;

    public ChatEvent(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

        // Do not listen to self. Probably not needed, but just in case.
        if (event.getUser().getName().equals("RNRPBot")) return;

        // Get the currently playing map
        if (event.getMessage().equalsIgnoreCase(prefix + "np")) {
            log.info("Sending !np command in " + channel);
            try {
                sendMessage(UsagiBot.getConfig().getNowPlayingMessage(event.getUser()));
            } catch (IOException e) {
                log.warn(e.getMessage());
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
            // Will be finished in 1.1.1
        }

        // Receive, parse, and send beatmap to Osu! and Twitch Chat
        if (event.getMessage().contains("https://osu.ppy.sh/")) {
            if (requestToggle) {
                log.info("Received possible osu song request. Parsing now...");
                beatmap = UsagiBot.getClient().getBeatmap(parseMessage(event.getMessage()));
                log.info("Beatmap ID Found: " + beatmap.getId());
                sendMessage(UsagiBot.getConfig().getTwitchMessage(beatmap, event.getUser()));
                sendIRCMessage(event.getUser(), beatmap);
            } else {
                sendMessage("You cannot request a beatmap at this time.");
            }
        }
    }

    // Send a message to the chat
    public void sendMessage(String message) {
        TwitchClient.client.getChat().sendMessage(channel, message);
    }

    // Send a message to Osu Client
    public void sendIRCMessage(EventUser user, Beatmap beatmap) {
        UsagiBot.getIrcBot().getUserChannelDao().getUser(UsagiBot.getConfig().getBanchoUsername()).send().message(UsagiBot.getConfig().getOsuIrcMessage(beatmap, user));
    }

    // Grabs the map digits
    public String parseMessage(String message) {
        String delimiters = "https?:\\/\\/osu.ppy.sh\\/(b|s|beatmapsets)\\/([0-9]*)(#osu|#taiko|#ctb|#maina)\\/([0-9]*)";
        Pattern urlPattern = Pattern.compile(delimiters, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = urlPattern.matcher(message);
        List<String> urlSplit = new ArrayList<String>();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                urlSplit.add(matcher.group(i));
            }
        }

        switch (urlSplit.size()) {
            case 1:
                sendMessage("Please send maps in the beatmapsets format.");
            case 2:
                sendMessage("Please send maps in the beatmapsets format.");
            case 3:
                sendMessage("Please send maps in the beatmapsets format.");
            case 4:
                String beatmap = urlSplit.get(3);
                return beatmap;
            default:
                log.warn("Scoin0 has not yet implemented all ways to request a beatmap. Only Beatmap Sets work right now.");
        }
        return null;
    }
}
