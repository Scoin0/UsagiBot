package usagibot.twitch.commands;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandEvent {

    private final ChannelMessageEvent event;
    private String[] args;
    private final CommandClient client;

    public CommandEvent (ChannelMessageEvent event, String[] args, CommandClient client) {
        this.event = event;
        this.args = args;
        this.client = client;
    }
}
