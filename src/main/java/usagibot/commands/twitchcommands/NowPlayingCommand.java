package usagibot.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.osu.api.Beatmap;
import usagibot.utils.Utility;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        aliases = new String[]{"np", "current", "map"};
    }

    /**
     * First figure out if GOsuMemory is running and if so, check the current song and send the beatmap in Twitch.
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {
        try {
            if (Utility.findGosumemory()) {
                Future<String> futureMods = Utility.fetchBeatmapModsInBackground();
                if (!futureMods.get().contains("NM")){
                    event.getClient().sendMessage(UsagiBot.getConfig().getLocalParsedMessage(UsagiBot.getConfig().getNowPlayingMessage() + " +" + futureMods.get(), event.getClient().getBeatmap(), event.getEvent().getUser()));
                } else {
                    event.getClient().sendMessage(UsagiBot.getConfig().getLocalParsedMessage(UsagiBot.getConfig().getNowPlayingMessage(), event.getClient().getBeatmap(), event.getEvent().getUser()));
                }
            } else {
                event.getClient().sendMessage("GOsumemory is not online!");
                log.warn("GOsumemory is not online!");
            }
        } catch (IOException e) {
            log.warn(e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
