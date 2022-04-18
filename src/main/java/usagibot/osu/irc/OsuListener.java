package usagibot.osu.irc;

import lombok.extern.slf4j.Slf4j;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

@Slf4j
public class OsuListener extends ListenerAdapter {

    @Override
    public void onConnect(ConnectEvent event) {
        log.info("Connected to Bancho IRC!");
    }
}