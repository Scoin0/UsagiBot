package usagibot.twitch.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.twitch.commands.Command;
import usagibot.twitch.commands.CommandEvent;

@Slf4j
public class RequestToggleCommand extends Command {

    private boolean requestToggle = true;

    public RequestToggleCommand() {
        name = "rq";
        description = "Turns on/off the requesting feature. (Default = on)";
        aliases = new String[]{"requesttoggle"};
        usage.add("rq");
        usage.add("rq toggle");
        usage.add("rq on|off");
        cooldown = 5;
    }

    @Override
    public void onCommand(CommandEvent event) {

        if (event.getArgs().length == 0) {
            log.info("The current status of requesting beatmaps is: " + requestToggle);
        } else {
            if (event.getArgs()[0].equalsIgnoreCase("toggle")) {
                requestToggle = !requestToggle;
                log.info("The current status of requesting beatmaps is: " + requestToggle);
            }

            if (event.getArgs()[0].equalsIgnoreCase("on")) {
                requestToggle = true;
                log.info("The current status of requesting beatmaps is: " + requestToggle);
            }

            if (event.getArgs()[0].equalsIgnoreCase("off")) {
                requestToggle = false;
                log.info("The current status of requesting beatmaps is: " + requestToggle);
            }
        }
    }
}
