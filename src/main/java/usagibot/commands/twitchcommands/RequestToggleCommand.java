package usagibot.commands.twitchcommands;

import lombok.Getter;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

@Getter
public class RequestToggleCommand extends Command {

    public static boolean requestToggle = true;

    /**
     * Turns on/off the requesting feature.
     */
    public RequestToggleCommand() {
        name = "requesttoggle";
        description = "Turns on/off the requesting feature. (Default = on)";
        aliases = new String[]{"rq"};
        usage.add("rq");
        usage.add("rq toggle");
        usage.add("rq on|off");
    }

    /**
     * Check the current status of the request toggle and switch it.
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {

        if (event.getArgs().length == 0) {
            event.getClient().sendMessage("The current status of requesting beatmaps is: " + requestToggle);
        } else if (event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            if (event.getArgs()[0].equalsIgnoreCase("toggle")) {
                requestToggle = !requestToggle;
                event.getClient().sendMessage("The current status of requesting beatmaps is: " + requestToggle);
            }

            if (event.getArgs()[0].equalsIgnoreCase("on")) {
                requestToggle = true;
                event.getClient().sendMessage("The current status of requesting beatmaps is: " + true);
            }

            if (event.getArgs()[0].equalsIgnoreCase("off")) {
                requestToggle = false;
                event.getClient().sendMessage("The current status of requesting beatmaps is: " + false);
            }
        }
    }
}
