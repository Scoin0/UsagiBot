package usagibot.twitch.commands.twitchcommands;

import usagibot.twitch.commands.Command;
import usagibot.twitch.commands.CommandEvent;

public class NowPlayingCommand extends Command {

    public NowPlayingCommand() {
        name = "np";
        description = "Sends the currently playing map to twitch chat.";
        aliases = new String[]{"nowplaying"};
    }

    @Override
    public void onCommand(CommandEvent event) {



    }

}
