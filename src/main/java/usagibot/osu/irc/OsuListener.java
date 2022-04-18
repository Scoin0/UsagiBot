package usagibot.osu.irc;

import kotlin.Suppress;
import lombok.extern.slf4j.Slf4j;
import org.pircbotx.InputParser;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

@Slf4j
public class OsuListener extends ListenerAdapter {

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        log.info("I have connected to BanchoIRC");
    }

    @Override
    public void onJoin(JoinEvent event) {
    }


    @Override
    public void onPart(PartEvent event) {
        return;
    }


    @Override
    public void onQuit(QuitEvent event) {
        return;
    }

    @Override
    public void onServerResponse(ServerResponseEvent event) {
        if (event.getCode() == 353) {
        }
    }

    public void InputParser(InputParser e) {
        e.close();
    }

}