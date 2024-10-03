package usagibot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import usagibot.configuration.BannedUsers;
import usagibot.configuration.Configuration;
import usagibot.osu.OsuClient;
import usagibot.osu.irc.OsuIrc;
import usagibot.osu.memreaders.MemoryReaderConnections;
import usagibot.twitch.TwitchClient;
import usagibot.utils.ConsoleColors;
import usagibot.utils.Constants;
import usagibot.utils.Utility;
import usagibot.utils.version.Version;
import usagibot.utils.version.VersionUpdate;
import usagibot.utils.version.VersionUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UsagiBot {

    @Getter
    static Configuration config = new Configuration();
    @Getter
    static BannedUsers bannedUsers = new BannedUsers();
    @Getter
    static OsuClient client;
    static PircBotX bot;
    @Getter
    static MemoryReaderConnections memoryReader;

    public static PircBotX getIrcBot() {
        return bot;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Constants.logo);
        config.initConfiguration();
        VersionUpdate.checkForUpdate();

        if (Utility.getJavaVersion() <= 20) {
            log.info(ConsoleColors.RED_BOLD_BRIGHT + "You are on Java Version: {}.", Utility.getJavaVersion());
            log.info(ConsoleColors.RED_BOLD_BRIGHT + "This may be the last version that will be in Java 8." +
                    "\nThe next version will be in Java 21. You can get the download from here: " +
                    ConsoleColors.BLUE_BOLD_BRIGHT + "https://www.oracle.com/java/technologies/downloads/"
                    + ConsoleColors.RESET + ConsoleColors.RED_BOLD_BRIGHT
                    + "\nIf you do not download the newest version of Java, you will not be able to download new updates for this program." +
                    ConsoleColors.RESET);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            Utility.shutdownExecutor();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    log.info("Awaiting Termination...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        executor.execute(() -> {
            while (true) {
                if ((MemoryReaderConnections.tosuRunning ? 1 : 0) + (MemoryReaderConnections.rosuRunning ? 1 : 0)
                        + (MemoryReaderConnections.streamCompanionRunning ? 1 : 0) + (MemoryReaderConnections.gosumemoryRunnning ? 1 : 0) == 0) {
                    memoryReader = new MemoryReaderConnections();
                } else {
                    MemoryReaderConnections.updateRunningPrograms();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.execute(() -> {
            TwitchClient twitchClient = new TwitchClient();
            twitchClient.startClient();
        });

        executor.execute(() -> {
            client = OsuClient.createClient(config.getOsuClientId(), config.getOsuAPIKey());
        });

        executor.execute(() -> {
            OsuIrc.Builder();
            bot = new PircBotX(OsuIrc.config);
            try {
                bot.startBot();
            } catch (IOException | IrcException e) {
                e.printStackTrace();
            }
        });
    }
}