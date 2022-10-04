package usagibot.commands.twitchcommands;

import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
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
    }

    /**
     * Send the current stats for the streamer
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        User user = event.getClient().getUser();
        StringBuilder stats = new StringBuilder();
        stats.append("Stats for " + user.getUsername() + ":");
        stats.append(" PP: " + nf.format(user.getStatistics().getPp()));
        stats.append(" | Rank: #" + nf.format(user.getStatistics().getGlobal_rank()));
        stats.append(" | Level: " + user.getStatistics().getLevel().getCurrent());
        stats.append(" | Accuracy: " + user.getStatistics().getHit_accuracy() + "%");
        stats.append(" | Play Count: " + nf.format(user.getStatistics().getPlay_count()));
        event.getClient().sendMessage(stats.toString());
    }
}
