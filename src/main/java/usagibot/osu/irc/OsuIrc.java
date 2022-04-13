package usagibot.osu.irc;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.Configuration;
import usagibot.UsagiBot;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OsuIrc {

    private int messageDelay = 250;
    private boolean reconnect = true;
    private int reconnectTimeout = 10000; // 10 Seconds
    public static Configuration config;

    private static String server = UsagiBot.getConfig().getBanchoServer();
    private static int port = UsagiBot.getConfig().getBanchoPort();
    private static String username = UsagiBot.getConfig().getBanchoUsername();
    private static String password = UsagiBot.getConfig().getBanchoPassword();
    private static String autoJoinChannel = UsagiBot.getConfig().getBanchoChannel();


    public static void Builder() {
        log.info("Starting Connection to Bancho IRC");
        config = new Configuration.Builder()
                .addServer(server, port)
                .setName(username)
                .addAutoJoinChannel(autoJoinChannel)
                .setServerPassword(password)
                .setAutoReconnect(false)
                .setEncoding(StandardCharsets.UTF_8)
                .addListener(new OsuListener())
                .buildConfiguration();
    }
}
