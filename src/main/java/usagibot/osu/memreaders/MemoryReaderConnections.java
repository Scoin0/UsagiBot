package usagibot.osu.memreaders;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.memreaders.gosu.GOsuMemoryReader;
import usagibot.osu.memreaders.rosu.ROsuReader;
import usagibot.osu.memreaders.streamcompanion.StreamCompanionReader;
import usagibot.osu.memreaders.tosu.TOsuReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Slf4j
@Getter
public class MemoryReaderConnections {

    public static final String webHookPath = UsagiBot.getConfig().getWebPath();
    public static boolean gosumemoryRunnning;
    public static boolean tosuRunning;
    public static boolean rosuRunning;
    public static boolean streamCompanionRunning;
    public static IMemoryReader memoryReader;

    public MemoryReaderConnections() {
        log.info("Attempting to find which osu memory reader is running...");
        updateRunningPrograms();
        if (gosumemoryRunnning) {
            log.info("gosumemory found!");
            memoryReader = new GOsuMemoryReader();
        } else if (tosuRunning) {
            log.info("tosu found!");
            memoryReader = new TOsuReader();
        } else if (rosuRunning) {
            log.info("rosu found!");
            memoryReader = new ROsuReader();
        } else if (streamCompanionRunning) {
            log.info("StreamCompanion found!");
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

    public String getSkin() {
        if (memoryReader == null)
            return null;

        try {
            return memoryReader.getSkin();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPP(int percentage) {
        if (memoryReader == null)
            return null;

        try {
            return memoryReader.getPP(percentage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateRunningPrograms() {
        gosumemoryRunnning = isProcessRunning("gosumemory.exe");
        tosuRunning = isProcessRunning("tosu.exe");
        rosuRunning = isProcessRunning("windows_rosu-memory.exe");
        streamCompanionRunning = isProcessRunning("osu!StreamCompanion.exe");
    }

    /**
     * Check which program is currently running
     * @param processName = The memory reader to be searched for
     * @return            = The programs name
     */
    public static boolean isProcessRunning(String processName) {
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
        try (InputStream inputStream = connection.getInputStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            boolean bomSkipped = false;

            // Check for BOM (Byte Order Mark)
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                if (!bomSkipped) {
                    if (bytesRead >= 3 && buffer[0] == (byte) 0xEF && buffer[1] == (byte) 0xBB && buffer[2] == (byte) 0xBF) {
                        // Skip BOM
                        outputStream.write(buffer, 3, bytesRead - 3);
                        bomSkipped = true;
                        continue;
                    } else {
                        // No BOM found, write the buffer as is
                        outputStream.write(buffer, 0, bytesRead);
                        bomSkipped = true;
                        continue;
                    }
                }
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toString("UTF-8");
        } finally {
            connection.disconnect();
        }
    }
    public static String fetchJsonDataWebSocket(String webSocketURI) {
        try {
            WebSocketConnector webSocketConnector = new WebSocketConnector(new URI(webSocketURI));
            webSocketConnector.connect();
            Thread.sleep(500); // Wait a second to fetch data
            webSocketConnector.close();
            return webSocketConnector.getJsonData();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}