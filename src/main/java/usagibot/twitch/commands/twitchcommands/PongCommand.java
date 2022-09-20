package usagibot.twitch.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.twitch.commands.Command;
import usagibot.twitch.commands.CommandEvent;

public class PongCommand extends Command {

    private final String channel = UsagiBot.getConfig().getTwitchChannel();

    public PongCommand() {
        name = "ping";
        description = "Ping Pong!";
    }

    @Override
    public void onCommand(CommandEvent event) {
        event.getEvent().getTwitchChat().sendMessage(channel, "Pong!");
    }
}
