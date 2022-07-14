package usagibot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClientBuilder;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.twitch.event.ChatEvent;

@Slf4j
public class TwitchClient {

    public static com.github.twitch4j.TwitchClient client;
    public static OAuth2Credential credentials = new OAuth2Credential("twitch", UsagiBot.getConfig().getTwitchPassword());

    public TwitchClient(){
        client = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(credentials)
                .withEnableHelix(true)
                .build();
    }

    public void startClient() {
        loadListeners();
        try {
            client.getChat().joinChannel(UsagiBot.getConfig().getTwitchChannel());
            log.info("Successfully Joined " + UsagiBot.getConfig().getTwitchChannel());
        } catch (Exception e) {
            log.warn("Could not connect to Twitch Channel. Is everything set up correctly?");
        }
    }

    public void loadListeners() {
        SimpleEventHandler eventHandler = client.getEventManager().getEventHandler(SimpleEventHandler.class);
        new ChatEvent(eventHandler);
    }

}
