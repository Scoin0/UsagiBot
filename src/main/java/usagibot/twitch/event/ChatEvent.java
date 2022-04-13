package usagibot.twitch.event;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.User;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.twitch.TwitchClient;

import java.util.regex.Pattern;

@Slf4j
public class ChatEvent {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();

    public ChatEvent(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event) {

        if (event.getUser().getName() == "RNRPBot") return;

        if (event.getMessage().equalsIgnoreCase(prefix + "np")) {
            log.info("Sending !np command in " + channel);
            sendMessage("NP Command");
        }

        if (event.getMessage().contains("https://osu.ppy.sh")) {
            String[] message = event.getMessage().split("#osu/");
            log.info(message.length + "" + message[1]);
            //beatmap = UsagiBot.getClient().getBeatmap("2965016");
            //UsagiBot.getIrcBot().getUserChannelDao().getUser("I_Only_Hit_100s").send().message("Map Received - " +
                    //"http://osu.ppy.sh/b/" + beatmap.getId());
        }

    }

    public void checkBadge(User user) {

    }

    public void sendMessage(String message) {
        TwitchClient.client.getChat().sendMessage(channel, message);
    }

    public void parseMessage(String message) {
    }
}
