package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.osu.api.GameMode;
import usagibot.osu.api.User;
import java.text.NumberFormat;
import java.util.Locale;

public class StatsCommand extends Command {

    /**
     * Sends streamers osu stats
     */
    public StatsCommand() {
        name = "stats";
        description = "Sends streamers osu stats";
        usage.add("stats");
        usage.add("stats <user>");
    }

    /**
     * Send the current stats for the streamer
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {
        if (event.getArgs().length == 0) {
            event.getClient().sendMessage(getStats(event.getClient().getUser()));
        }

        if (event.getArgs().length >= 1) {
            try {
                User user = UsagiBot.getClient().getUser(event.getArgs()[0], GameMode.OSU);
                event.getClient().sendMessage(getStats(user));
            } catch (Exception e) {
                event.getClient().sendMessage("User not found.");
            }
        }
    }

    /**
     * Grabs the stats of the user
     * @param user  The user to be grabbed
     * @return      Returns all the stats of the user
     */
    public String getStats(User user) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        String stats = "Stats for " + user.getUsername() + ":" +
                " PP: " + nf.format(user.getStatistics().getPp()) +
                " | Rank: #" + nf.format(user.getStatistics().getGlobal_rank()) +
                " | Level: " + user.getStatistics().getLevel().getCurrent() +
                " | Accuracy: " + user.getStatistics().getHit_accuracy() + "%" +
                " | Play Count: " + nf.format(user.getStatistics().getPlay_count());
        return stats;
    }
}
