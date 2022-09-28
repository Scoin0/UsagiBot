package usagibot.commands;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandEvent {

    private final ChannelMessageEvent event;
    private String[] args;
    private final CommandClient client;

    /**
     * The CommandEvent Constructor
     * @param event     Twitch4J's ChannelMessageEvent
     * @param args      Arguments from each command
     * @param client    The Command Client
     */
    public CommandEvent (ChannelMessageEvent event, String[] args, CommandClient client) {
        this.event = event;
        this.args = args;
        this.client = client;
    }
}
