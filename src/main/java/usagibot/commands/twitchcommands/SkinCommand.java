package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

public class SkinCommand extends Command {

    public SkinCommand() {
        name = "skin";
        description = "Gets the current skin the streamer is using";
        usage.add("skin");
        usage.add("skin toggle (Streamer only)");
        usage.add("skin on|off (Streamer only)");
    }

    @Override
    public void onCommand(CommandEvent event) {

        boolean skinToggle = UsagiBot.getConfig().isSkinCommand();

        if (skinToggle) {
            event.getClient().sendMessage("Skin is: " + UsagiBot.getMemoryReader().getSkin());
        }

        if (event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            if (event.getArgs()[0].equalsIgnoreCase("toggle")) {
                skinToggle = !skinToggle;
                event.getClient().sendMessage("The current status of requesting the skin is: " + skinToggle);
                try {
                    UsagiBot.getConfig().setSkinCommand(skinToggle);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (event.getArgs()[0].equalsIgnoreCase("on")) {
                skinToggle = true;
                event.getClient().sendMessage("The current status of requesting the skin is: " + true);
                try {
                    UsagiBot.getConfig().setSkinCommand(skinToggle);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (event.getArgs()[0].equalsIgnoreCase("off")) {
                skinToggle = false;
                event.getClient().sendMessage("The current status of requesting the skin is: " + false);
                try {
                    UsagiBot.getConfig().setSkinCommand(skinToggle);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}