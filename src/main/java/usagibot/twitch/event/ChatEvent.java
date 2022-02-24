package usagibot.twitch.event;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import usagibot.UsagiBot;
import usagibot.twitch.Client;

public class ChatEvent {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();

    public ChatEvent(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event) {

        if(event.getMessage().equalsIgnoreCase(prefix + "np")) {
            sendMessage("A test of messaging. " + UsagiBot.class.getPackage().getImplementationVersion());
        }
    }

    public void sendMessage(String message) {
        Client.client.getChat().sendMessage(channel, message);
    }
}
