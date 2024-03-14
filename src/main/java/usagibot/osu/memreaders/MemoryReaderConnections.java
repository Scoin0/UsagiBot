package usagibot.osu.memreaders;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.gosu.GOsuMemoryReader;
import usagibot.osu.memreaders.rosu.ROsuReader;
import usagibot.osu.memreaders.streamcompanion.StreamCompanionReader;
import usagibot.osu.memreaders.tosu.TOsuReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Getter
public class MemoryReaderConnections {

    public static final String webHookPath = UsagiBot.getConfig().getGOsuUrlPath();
    private static boolean gosumemoryRunnning;
    private static boolean tosuRunning;
    private static boolean rosuRunning;
    private static boolean streamCompanionRunning;
    public static IMemoryReader memoryReader;

    public MemoryReaderConnections() {
        log.info("Attempting to find which osu memory reader is running...");
        updateRunningPrograms();
        if (gosumemoryRunnning) {
            log.info("gosumemory found!");
            memoryReader = new GOsuMemoryReader();
        } else if (tosuRunning) {
            memoryReader = new TOsuReader();
        } else if (rosuRunning) {
            memoryReader = new ROsuReader();
        } else if (streamCompanionRunning) {
            memoryReader = new StreamCompanionReader();
        } else {
            log.warn("There are no memory readers running or it is not within the list of supported memory readers.");
        }
    }

    public Beatmap getSong() {
        if (memoryReader == null) {
            return null;
        }

        try {
            return memoryReader.getSong();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMods() {
        if (memoryReader == null)
            return null;

        try {
            return memoryReader.getMods();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void updateRunningPrograms() {
        gosumemoryRunnning = isProcessRunning("gosumemory.exe");
        tosuRunning = isProcessRunning("tosu.exe");
        rosuRunning = isProcessRunning("rosu.exe");
        streamCompanionRunning = isProcessRunning("streamcompanion.exe");
    }

    /**
     * Check which program is currently running
     * @param processName = The memory reader to be searched for
     * @return            = The programs name
     */
    private static boolean isProcessRunning(String processName) {
        if (System.getProperty("os.name").startsWith("Windows")) {
            try {
                Process process = Runtime.getRuntime().exec("tasklist");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    if (line.contains(processName)) {
                        return true;
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String fetchJsonData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up the connection
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // Read the response
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}