package usagibot.commands.twitchcommands;

import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

public class SkinCommand extends Command {

    public SkinCommand() {
        name = "skin";
        description = "Gets the current skin the streamer is using";
        usage.add("skin");
    }

    @Override
    public void onCommand(CommandEvent event) {
        event.getClient().sendMessage("Skin is: " + UsagiBot.getMemoryReader().getSkin());
    }
}
