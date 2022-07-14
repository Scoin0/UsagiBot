package usagibot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import usagibot.UsagiBot;
import usagibot.osu.objects.Beatmap;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class Utility {

    static Beatmap beatmap;
    private static final String webHookPath = UsagiBot.getConfig().getGOsuUrlPath();

    /**
     * Converts integer time to human-readable time
     * @param totalTime The total time in integer time
     * @return  Human-readable time
     */
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

    /**
     * Grabs the beatmap ID from gosumemory
     * @return              The beatmap id
     * @throws IOException  If it cannot find the location of the file
     */
    public static Beatmap getSongFromGosuMemory() throws IOException {
        JSONObject t = (JSONObject) (new JSONTokener(IOUtils.toString((new URL(webHookPath)).openStream()))).nextValue();
        String beatmapID = t.getJSONObject("menu").getJSONObject("bm").get("id").toString();
        beatmap = UsagiBot.getClient().getBeatmap(beatmapID);
        return beatmap;
    }

}
