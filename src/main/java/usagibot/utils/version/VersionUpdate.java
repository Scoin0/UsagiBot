package usagibot.utils.version;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import usagibot.utils.Constants;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

@Slf4j
public class VersionUpdate {

    static Version latest = VersionUtil.getLatestVersion();

    private static String currentFileName;
    private static String updatedFileName = "UsagiBot.jar.update";

    static {
        try {
            currentFileName = new File(VersionUpdate.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            log.info(currentFileName);
        } catch (URISyntaxException e) {
            currentFileName = "UsagiBot.jar";
            log.error("Unable to get path of the JAR file!");
            e.printStackTrace();
        }
    }

    /**
     * Check for an update and download the latest version if one is found
     */
    public static void checkForUpdate() {
        if (!latest.compareVersion(Constants.version)) {
            log.info("You are up to date.");
            return;
        }
        log.info("Update was found! (Current Build: " + Constants.version + ") | (Latest Build: " + latest.toString() + ")");
        log.info("Updating now...");
        try {
            String latestVersion = getUpdatedBotURL(VersionUtil.getLatestVersion());
            downloadLatestVersionAndRestart(latestVersion, true);
            log.info("Downloaded Updated Build...");
        } catch (IOException e) {
            log.warn("Unable to write the file...");
            e.printStackTrace();
        }
    }

    /**
     * Grabs the updated version's link
     * @param latestVersion The latest version
     * @return              The link to the latest build version
     */
    public static String getUpdatedBotURL(Version latestVersion) {
        return "https://github.com/Scoin0/UsagiBot/releases/download/" + latestVersion + "/UsagiBot.jar";
    }

    /**
     * Downloads the latest version, applies the update, and restarts the program.
     * @param updatedBotURL     The new url for the update
     * @param shouldLaunchNow   Should the update start right away?
     * @throws IOException      Occurs if the file could not be located or placed.
     */
    private static void downloadLatestVersionAndRestart(String updatedBotURL, boolean shouldLaunchNow) throws IOException {
        Response response;
        response = Jsoup.connect(updatedBotURL)
                .ignoreContentType(true)
                .timeout(60*1000)
                .maxBodySize(1024 * 1024 * 100)
                .execute();

        try (FileOutputStream out = new FileOutputStream(updatedFileName)) {
            out.write(response.bodyAsBytes());
        }

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            final String batchFile = "update-UsagiBot.bat";
            final String batchPath = new File(batchFile).getAbsolutePath();

            String script = "@echo off\r\n"
                    + "timeout 1\r\n"
                    + "copy " + updatedFileName + " " + currentFileName + "\r\n"
                    + "del " + updatedFileName + "\r\n";
            if (shouldLaunchNow) {
                script += "java -jar " + currentFileName + "\r\n";
            }

            script += "del " + batchPath + "\r\n";

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(batchFile))) {
                bufferedWriter.write(script);
                bufferedWriter.flush();
            }

            log.info("Saved update script to " + batchFile);

            List<String> cmds = Arrays.asList("cmd.exe", "/C", "start", batchFile);
            ProcessBuilder processBuilder = new ProcessBuilder(cmds);
            log.info(batchPath + batchFile);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log.info("Running update script...");
                    Process buildProcess = processBuilder.start();
                    String line = "";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(buildProcess.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + System.lineSeparator());
                    }
                    buildProcess.destroy();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            log.info ("Updated applied! Restarting!");
            System.exit(200);
        }
    }
}