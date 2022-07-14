package usagibot.osu.irc;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.Configuration;
import usagibot.UsagiBot;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OsuIrc {

    public static Configuration config;

    private final static String server = UsagiBot.getConfig().getBanchoServer();
    private final static int port = UsagiBot.getConfig().getBanchoPort();
    private final static String username = UsagiBot.getConfig().getBanchoUsername();
    private final static String password = UsagiBot.getConfig().getBanchoPassword();
    private final static String autoJoinChannel = UsagiBot.getConfig().getBanchoChannel();

    /**
     * The pircbot IRC builder
     */
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
