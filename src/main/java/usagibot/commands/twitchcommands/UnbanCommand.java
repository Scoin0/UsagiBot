package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

import java.util.Locale;

public class UnbanCommand extends Command {

    public UnbanCommand() {
        name = "unban";
        description = "Unban a user from using the bot.";
        usage.add("unban");
        usage.add("unban {username}");
        aliases = new String[]{"unbanuser", "unbu"};
    }

    @Override
    public void onCommand(CommandEvent event) {

        if (event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            if (event.getArgs().length == 0) {
                event.getClient().sendMessage("Please supply a username.");
            }

            if (event.getArgs().length >= 1) {
                String unbannedUser = event.getArgs()[0].toLowerCase(Locale.ROOT);
                if (!UsagiBot.getBannedUsers().bannedUsers.contains(unbannedUser)) {
                    event.getClient().sendMessage(unbannedUser + " is not banned.");
                } else {
                    UsagiBot.getBannedUsers().removeBannedUser(unbannedUser);
                    event.getClient().sendMessage(unbannedUser + " has been unrestricted from using the bot.");
                }
            }
        }
    }
}