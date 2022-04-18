package usagibot.utils;

import com.github.twitch4j.common.events.domain.EventUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import usagibot.UsagiBot;
import usagibot.osu.objects.Beatmap;
import usagibot.utils.version.Version;
import usagibot.utils.version.VersionUtil;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class Utility {

    static Beatmap beatmap;
    private static final String webHookPath = UsagiBot.getConfig().getGOsuUrlPath();

    // Converts int time to human-readable time
    public static String convertTime(int totalTime) {
        int hours = totalTime / 3600;
        int minutes = (totalTime % 3600) / 60;
        int seconds = totalTime % 60;

        String h = (hours > 0 ? hours + ":" : "");
        String m = (minutes < 10 && minutes > 0 && hours > 0 ? "0" : "")
                + (minutes > 0 ? (hours > 0 && seconds == 0 ? String.valueOf(minutes) : minutes + ":") : "");
        String s = (seconds == 0 && (hours > 0 || minutes > 0) ? "" : (seconds < 10 && (hours > 0 || minutes > 0) ? "0" : "")
                + seconds + "");

        return h + (hours > 0 ? "" : "") + m + (minutes > 0 ? "" : "") + s;
    }

    // Grab the map ID from Gosumemory
    public static Beatmap getSongFromGosuMemory() throws IOException {
        JSONObject t = (JSONObject) (new JSONTokener(IOUtils.toString((new URL(webHookPath)).openStream()))).nextValue();
        String beatmapID = t.getJSONObject("menu").getJSONObject("bm").get("id").toString();
        beatmap = UsagiBot.getClient().getBeatmap(beatmapID);
        return beatmap;
    }

    // The message to send to BanchoIRC
    public static String ircMessage(EventUser user, Beatmap beatmap) {
        return String.format("[%s] > [%s %s - %s [%s]] ♫ %s ★ %.2f BPM:%.1f AR:%.1f OD:%.1f", user.getName(),
                beatmap.getUrl(), beatmap.getBeatmapset().getArtist(), beatmap.getBeatmapset().getTitle(),
                beatmap.getVersion(), Utility.convertTime(beatmap.getTotal_length()), beatmap.getDifficulty_rating(),
                beatmap.getBpm(), beatmap.getAr(), beatmap.getDrain());
    }

    // The message to send to Twitch
    public static String receivedMessage(Beatmap beatmap) {
        return  String.format("[RECEIVED] > [%s] %s - %s [%s] ♫ %s ★ %.2f BPM:%.1f AR:%.1f OD:%.1f",
                beatmap.getStatus(), beatmap.getBeatmapset().getArtist(), beatmap.getBeatmapset().getTitle(),
                beatmap.getVersion(), Utility.convertTime(beatmap.getTotal_length()), beatmap.getDifficulty_rating(),
                beatmap.getBpm(), beatmap.getAr(), beatmap.getDrain());
    }
}
