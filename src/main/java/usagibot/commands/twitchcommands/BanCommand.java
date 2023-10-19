package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

public class BanCommand extends Command {

    public BanCommand() {
        name = "ban";
        description = "Ban a user from using the bot.";
        usage.add("ban");
        usage.add("ban {username}");
        aliases = new String[]{"banuser", "bu"};
    }

    @Override
    public void onCommand(CommandEvent event) {

        if (event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            if (event.getArgs().length == 0) {
                event.getClient().sendMessage("Please supply a username.");
            }

            if (event.getArgs().length >= 1) {
                String bannedUser = event.getArgs()[0];
                if (UsagiBot.getBannedUsers().bannedUsers.contains(bannedUser)) {
                    event.getClient().sendMessage(bannedUser + " has already been restricted from using the bot.");
                } else {
                    UsagiBot.getBannedUsers().addBannedUser(bannedUser);
                    event.getClient().sendMessage(bannedUser + " has been restricted from using the bot.");
                }
            }
        }
    }
}