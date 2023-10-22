package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.configuration.BannedUsers;

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
        if (!event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            return; // Ignore commands from other users
        }

        if (event.getArgs().length == 0) {
            event.getClient().sendMessage("Please supply a username.");
            return;
        }

        String bannedUser = event.getArgs()[0];
        BannedUsers bannedUsers = UsagiBot.getBannedUsers();

        if (!bannedUsers.bannedUsers.contains(bannedUser.toLowerCase())) {
            event.getClient().sendMessage(bannedUser + " is not restricted.");
        } else {
            bannedUsers.removeBannedUser(bannedUser);
            event.getClient().sendMessage(bannedUser + " has been unrestricted from using the bot.");
        }
    }
}