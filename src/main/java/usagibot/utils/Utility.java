package usagibot.utils;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Utility {

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

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
        return executor.submit(() -> UsagiBot.getMemoryReader().getSong());
    }

    public static Future<String> fetchBeatmapModsInBackground() {
        return executor.submit(() -> UsagiBot.getMemoryReader().getMods());
    }

    public static void shutdownExecutor() {
        executor.shutdown();
    }

    // Get Java Version
    public static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        }
        return Integer.parseInt(version);
    }
}