package usagibot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.CommandClient;
import usagibot.commands.twitchcommands.*;

@Slf4j
public class TwitchClient {

    public static com.github.twitch4j.TwitchClient client;
    public static OAuth2Credential credentials = new OAuth2Credential("twitch", UsagiBot.getConfig().getTwitchPassword());
    CommandClient commandClient;

    /**
     * The Twitch IRC client constructor
     */
    public TwitchClient(){
        client = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(credentials)
                .withEnableHelix(true)
                .build();
    }

    /**
     * Joins the channel and loads the chat listener
     */
    public void startClient() {
        loadListeners();
        try {
            client.getChat().joinChannel(UsagiBot.getConfig().getTwitchChannel());
            log.info("Successfully Joined " + UsagiBot.getConfig().getTwitchChannel());
        } catch (Exception e) {
            log.warn("Could not connect to Twitch Channel. Is everything set up correctly?");
        }
    }

    /**
     * Loads the chat listener
     */
    public void loadListeners() {
        SimpleEventHandler eventHandler = client.getEventManager().getEventHandler(SimpleEventHandler.class);
        commandClient = new CommandClient(eventHandler);
        addCommands();
    }

    /**
     * Add commands to the list of current avaliable commands
     */
    public void addCommands() {
        commandClient.addCommand(new BanCommand());
        commandClient.addCommand(new UnbanCommand());
        commandClient.addCommand(new HelpCommand());
        commandClient.addCommand(new NowPlayingCommand());
        commandClient.addCommand(new RequestToggleCommand());
        commandClient.addCommand(new StarLimitCommand());
        commandClient.addCommand(new StatsCommand());
    }
}
