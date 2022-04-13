package usagibot.osu.irc;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.InputParser;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;
import usagibot.UsagiBot;
import usagibot.osu.objects.Beatmap;

@Slf4j
public class OsuListener extends ListenerAdapter {

    Beatmap beatmap;
    boolean slient = true;

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        log.info("I have connected.");
        beatmap = UsagiBot.getClient().getBeatmap("3206350");
        String link = "[https://osu.ppy.sh/beatmapsets/" + beatmap.getBeatmapset_id() + "#osu/" + beatmap.getId() + " " + beatmap.getBeatmapset().getTitle() + " - " + beatmap.getBeatmapset().getArtist() + "] [" + beatmap.getVersion() + "]";
        UsagiBot.getIrcBot().getUserChannelDao().getUser("I_Only_Hit_100s").send().message(link);
    }

    @Override
    public void onJoin(JoinEvent event) {
    }


    @Override
    public void onPart(PartEvent event) {
        return;
    }


    @Override
    public void onQuit(QuitEvent event) {
        return;
    }

    @Override
    public void onServerResponse(ServerResponseEvent event) {
        if (event.getCode() == 353) {
        }
    }

    public void InputParser(InputParser e) {
        e.close();
    }

}