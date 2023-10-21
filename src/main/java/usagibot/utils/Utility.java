package usagibot.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Utility {

    static Beatmap beatmap;
    private static final String webHookPath = UsagiBot.getConfig().getGOsuUrlPath();
    private static final ExecutorService executor = Executors.newFixedThreadPool(1);

    /**
     * Converts integer time to human-readable time
     * @param totalTime The total time in integer time
     * @return  Human-readable time
     */
    public static String convertTime(int totalTime) {
        Duration time = Duration.ofSeconds(totalTime);
        int hours = totalTime / 3600;
        int minutes = (totalTime % 3600) / 60;
        int seconds = totalTime % 60;

        String formattedTime = "";

        if (hours > 0) {
            formattedTime += String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            formattedTime += String.format("%d:%02d", minutes, seconds);
        } else {
            formattedTime += String.format("0:%02d", seconds);
        }

        return formattedTime;
    }

    public static Future<Beatmap> fetchBeatmapInBackground() {
        return executor.submit(() -> {
            try {
                return getSongFromGosuMemory();
            } catch (IOException e) {
                log.warn(e.getMessage());
                return null;
            }
        });
    }

    public static Future<String> fetchBeatmapModsInBackground() {
        return executor.submit(() -> {
            try {
                return getModsFromGosuMemory();
            } catch (IOException e) {
                log.warn(e.getMessage());
                return null;
            }
        });
    }

    /**
     * Grabs the beatmap ID from gosumemory
     * @return              The beatmap id
     * @throws IOException  If it cannot find the location of the file
     */
    public static Beatmap getSongFromGosuMemory() throws IOException {
        HttpURLConnection connection = openConnection(webHookPath);

        try (InputStream inputStream = connection.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);
            String beatmapID = rootNode.path("menu").path("bm").path("id").asText();
            return beatmap = UsagiBot.getClient().getBeatmap(beatmapID);
        }
    }

    /**
     * Grabs the mods being played from gosumemory
     * @return              The mods being played
     * @throws IOException  If it cannot find the location of the file
     */
    public static String getModsFromGosuMemory() throws IOException {
        HttpURLConnection connection = openConnection(webHookPath);

        try (InputStream inputStream = connection.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(inputStream);
            String mods = rootNode.path("menu").path("mods").path("str").toString();
            return mods.replaceAll("^\"|\"$", "");
        }
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

    private static HttpURLConnection openConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}