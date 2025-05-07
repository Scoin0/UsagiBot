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
            return;
        }

        String[] args = event.getArgs();

        if (args.length == 0 || args[0].trim().isEmpty()) {
            event.getClient().sendMessage("For SS: " + UsagiBot.getMemoryReader().getPP(100) + "pp");
            return;
        }

        try {
            double percentage = Double.parseDouble(args[0].trim());
            int roundedPercentage = (int) Math.round(percentage);

            if (roundedPercentage >= 95 && roundedPercentage <= 100) {
                event.getClient().sendMessage("PP at " + roundedPercentage + "% : " + UsagiBot.getMemoryReader().getPP(roundedPercentage) + "pp.");
            } else {
                event.getClient().sendMessage("Percentages must be between 95 and 100. This number is rounded upwards.");
            }
        } catch (NumberFormatException e) {
            event.getClient().sendMessage("Invalid percentage format. Please enter a number between 95 and 100.");
        }
    }
}