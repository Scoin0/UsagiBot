package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.configuration.BannedUsers;

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
        if (!event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel().toLowerCase())) {
            return; // Ignore commands from other users
        }

        if (event.getArgs().length == 0) {
            event.getClient().sendMessage("Please supply a username.");
            return;
        }

        String bannedUser = event.getArgs()[0];
        BannedUsers bannedUsers = UsagiBot.getBannedUsers();

        if (bannedUsers.bannedUsers.contains(bannedUser.toLowerCase())) {
            event.getClient().sendMessage(bannedUser + " has already been restricted from using the bot.");
        } else {
            bannedUsers.addBannedUser(bannedUser);
            event.getClient().sendMessage(bannedUser + " has been restricted from using the bot.");
        }
    }
}