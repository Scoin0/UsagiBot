package usagibot;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import usagibot.configuration.BannedUsers;
import usagibot.configuration.Configuration;
import usagibot.osu.OsuClient;
import usagibot.osu.irc.OsuIrc;
import usagibot.osu.memreaders.MemoryReaderConnections;
import usagibot.twitch.TwitchClient;
import usagibot.utils.Constants;
import usagibot.utils.Utility;
import usagibot.utils.version.VersionUpdate;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UsagiBot {

    static Configuration config = new Configuration();
    static BannedUsers bannedUsers = new BannedUsers();
    static OsuClient client;
    static PircBotX bot;
    static MemoryReaderConnections memoryReader;

    public static Configuration getConfig() {
        return config;
    }

    public static BannedUsers getBannedUsers() {
        return bannedUsers;
    }

    public static OsuClient getClient() {
        return client;
    }

    public static PircBotX getIrcBot() {
        return bot;
    }

    public static MemoryReaderConnections getMemoryReader() {
        return memoryReader;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Constants.logo);
        config.initConfiguration();
        VersionUpdate.checkForUpdate();

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

        executor.execute(() -> memoryReader = new MemoryReaderConnections());

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