package usagibot.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

@Slf4j
public class StarLimitCommand extends Command {

    /**
     * Set the star limit
     */
    public StarLimitCommand() {
        name = "starlimit";
        description = "Set the star limit";
        usage.add("sl <number>");
        usage.add("starlimit <number>");
        aliases = new String[]{"sl"};
    }

    /**
     * Set or show the Star Limit
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {
        if (event.getArgs().length == 0) {
            event.getClient().sendMessage("The current star limit is: " + UsagiBot.getConfig().getOsuStarLimit() + "*");
        }

        if (event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            if (event.getArgs().length == 1) {
                try {
                    double newStarLimit = Double.parseDouble(event.getArgs()[0]);
                    if (newStarLimit <= 0 || newStarLimit >= 13) {
                        event.getClient().sendMessage("Please enter a number between 0.1 and 12.99!");
                    } else {
                        UsagiBot.getConfig().setOsuStarLimit(newStarLimit);
                        event.getClient().sendMessage("The star limit has been changed to " + newStarLimit);
                        log.info("The star limit has been changed to " + newStarLimit);
                    }
                } catch (NumberFormatException e) {
                    event.getClient().sendMessage("You've sent something that is not a number.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
