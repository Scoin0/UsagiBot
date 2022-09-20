package usagibot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.twitch.commands.Command;
import usagibot.twitch.commands.CommandClient;
import usagibot.twitch.commands.CommandEvent;
import usagibot.twitch.commands.twitchcommands.HelpCommand;
import usagibot.twitch.commands.twitchcommands.PongCommand;
import usagibot.twitch.commands.twitchcommands.RequestToggleCommand;
import usagibot.twitch.event.ChatEvent;

import java.beans.EventHandler;

@Slf4j
public class TwitchClient {

    public static com.github.twitch4j.TwitchClient client;
    public static OAuth2Credential credentials = new OAuth2Credential("twitch", UsagiBot.getConfig().getTwitchPassword());
    CommandClient clientC;

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
        clientC = new CommandClient(eventHandler);
        addCommands();
    }

    public void addCommands() {
        clientC.addCommand(new PongCommand());
        clientC.addCommand(new HelpCommand());
        clientC.addCommand(new RequestToggleCommand());
    }
}
