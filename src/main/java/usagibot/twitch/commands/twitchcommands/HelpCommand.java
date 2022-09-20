package usagibot.twitch.commands.twitchcommands;

import usagibot.twitch.commands.Command;
import usagibot.twitch.commands.CommandEvent;
import java.util.Optional;

public class HelpCommand extends Command {

    public HelpCommand() {
        name = "help";
        description = "Sends a link to the command wiki page";
        usage.add("help");
        usage.add("help {command}");
        aliases = new String[]{"commands"};
    }

    @Override
    public void onCommand(CommandEvent event) {

        String[] args = event.getArgs();

        if (event.getArgs().length == 0) {
            sendHelp(event);
        } else {
            sendCommandHelp(event, args[0]);
        }
    }

    private void sendHelp(CommandEvent event) {

    }

    private void sendCommandHelp(CommandEvent event, String commandName) {

        boolean isFound;
        Optional<Command> command = event.getClient().getCommands().stream().filter(c -> c.getName().equalsIgnoreCase(commandName)).findAny();
        if (command.isPresent()) {
            isFound = !command.get().getName().equalsIgnoreCase("unassigned");
        } else {
            isFound = false;
        }

        if (isFound) {
            System.out.println(command.get().getName() + " " + command.get().getDescription());
        } else {
            System.out.println("Command not found.");
        }
    }

}
