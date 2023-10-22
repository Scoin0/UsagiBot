package usagibot.osu.irc;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

@Slf4j
public class OsuListener extends ListenerAdapter {

    /**
     * Tells when the bot has successfully connected to Bancho
     * @param event Fired upon connection
     */
    @Override
    public void onConnect(ConnectEvent event) {
        log.info("Connected to Bancho IRC!");
    }
}