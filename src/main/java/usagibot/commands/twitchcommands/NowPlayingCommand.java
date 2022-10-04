package usagibot.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.utils.Utility;

import java.io.IOException;

@Slf4j
public class NowPlayingCommand extends Command {

    /**
     * Get the currently playing map and send it as a link
     */
    public NowPlayingCommand() {
        name = "nowplaying";
        description = "Sends the currently playing map to twitch chat.";
        usage.add("np");
        usage.add("nowplaying");
        aliases = new String[]{"np"};
    }

    /**
     * First figure out if GOsuMemory is running and if so, check the current song and send the beatmap in Twitch.
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {

        try {
            if (Utility.findGosumemory()) {
                String mods = Utility.getModsFromGosuMemory();
                if (!mods.contains("NM")){
                    event.getClient().sendMessage(UsagiBot.getConfig().getLocalParsedMessage(UsagiBot.getConfig().getNowPlayingMessage() + " +" + mods, event.getClient().getBeatmap(), event.getEvent().getUser()));
                } else {
                    event.getClient().sendMessage(UsagiBot.getConfig().getLocalParsedMessage(UsagiBot.getConfig().getNowPlayingMessage(), event.getClient().getBeatmap(), event.getEvent().getUser()));
                }
            } else {
                event.getClient().sendMessage("GOsumemory is not online!");
                log.warn("GOsumemory is not online!");
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }
}
