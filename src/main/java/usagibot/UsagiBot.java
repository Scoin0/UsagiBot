package usagibot;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import usagibot.configuration.Configuration;
import usagibot.osu.OsuClient;
import usagibot.osu.irc.OsuIrc;
import usagibot.osu.objects.Beatmap;
import usagibot.twitch.TwitchClient;

import java.io.IOException;

@Slf4j
public class UsagiBot {

    static Configuration config = new Configuration();
    static OsuClient client;
    static PircBotX bot;

    private static Thread osuClientThread;
    private static Thread twitchThread;
    private static Thread osuIrcThread;

    public static Configuration getConfig() {
        return config;
    }

    public static OsuClient getClient() {
        return client;
    }

    public static PircBotX getIrcBot(){
        return bot;
    }

    public static void main(String[] args) throws Exception {
        log.info("Welcome to UsagiBot!");
        config.initConfiguration();


        twitchThread = new Thread(() -> {
            twitchThread.setName("Twitch");
            TwitchClient twitchClient1 = new TwitchClient();
            twitchClient1.startClient();
        });

        osuClientThread = new Thread(() -> {
            osuClientThread.setName("Osu Client");
            client = OsuClient.createClient(config.getOsuClientId(), config.getOsuAPIKey());
            Beatmap beatmap = client.getBeatmap("2965016");
            log.info("Getting Beatmap URL "
            + beatmap.getUrl());
        });

        osuIrcThread = new Thread(() -> {
            osuIrcThread.setName("Osu IRC");
            OsuIrc.Builder();
            bot = new PircBotX(OsuIrc.config);
            try {
                bot.startBot();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IrcException e) {
                e.printStackTrace();
            }
        });

        twitchThread.start();
        osuClientThread.start();
        osuIrcThread.start();
    }
}
