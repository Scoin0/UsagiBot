package usagibot.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.osu.memreaders.MemoryReaderConnections;

@Slf4j
public class PPCommand extends Command {

    public PPCommand() {
        name = "pp";
        description = "Gets the PP of the current map. Choose between 95-100%. Default is SS.";
        usage.add("pp");
        usage.add("pp <percentage>");
    }

    @Override
    public void onCommand(CommandEvent event) {

        if (MemoryReaderConnections.gosumemoryRunnning) {
            event.getClient().sendMessage("Gosumemory currently does not support pp calculations.");
        } else if (event.getArgs().length == 0) {
            event.getClient().sendMessage("For SS: " + UsagiBot.getMemoryReader().getPP(100) + "pp");
        } else {
            int percentage = (int) Math.round(Double.parseDouble(event.getArgs()[0]));

            if (percentage >= 95 && percentage <= 100) {
                event.getClient().sendMessage("PP at " + percentage + "% : " + UsagiBot.getMemoryReader().getPP(percentage) + "pp.");
            } else {
                event.getClient().sendMessage("Percentages must be between 95 and 100. It rounds to the nearest int.");
            }
        }
    }
}