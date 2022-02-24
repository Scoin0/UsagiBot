package usagibot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import usagibot.UsagiBot;
import usagibot.twitch.event.ChatEvent;

public class Client {

    public static TwitchClient client;
    public static OAuth2Credential credentials = new OAuth2Credential("twitch", UsagiBot.getConfig().getTwitchPassword());

    public Client (){
        client = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(credentials)
                .withEnableHelix(true)
                .build();
    }

    public void startClient() {
        loadListeners();
        client.getChat().joinChannel(UsagiBot.getConfig().getTwitchChannel());
    }

    public void loadListeners() {
        SimpleEventHandler eventHandler = client.getEventManager().getEventHandler(SimpleEventHandler.class);
        new ChatEvent(eventHandler);
    }
}
