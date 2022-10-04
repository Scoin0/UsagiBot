package usagibot.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONTokener;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.Mods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Grabs the mods being played from gosumemory
     * @return              The mods being played
     * @throws IOException  If it cannot find the location of the file
     */
    public static String getModsFromGosuMemory() throws IOException {
        JSONObject t = (JSONObject) (new JSONTokener(IOUtils.toString((new URL(webHookPath)).openStream()))).nextValue();
        String mods = t.getJSONObject("menu").getJSONObject("mods").get("str").toString();
        return mods;
    }

    /**
     * Converts names that use spaces with ones that have underscores
     * @param message   The message to be changed
     * @return          The underscored message
     */
    public static String removeSpaces(String message) {
        String underscoreMessage = message.replaceAll(" ", "_");
        return underscoreMessage;
    }

    /**
     * Search to see if GOsuMemory is running first before starting the GOsuMemory Thread
     * @return True or false depending on if the requested program is running.
     */
    public static boolean findGosumemory() {
        // Okay, to be honest, I'm drunk. This is fucked.
        String[] firstSplit = webHookPath.split(":");
        String startingResult = firstSplit[2];
        String[] secondSplit = startingResult.split("/");
        try (Socket ignored = new Socket("localhost", Integer.parseInt(secondSplit[0]))) {
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
