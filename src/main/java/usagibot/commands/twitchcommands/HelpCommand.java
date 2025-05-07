package usagibot.commands.twitchcommands;

import lombok.extern.slf4j.Slf4j;
import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

import java.util.Optional;

@Slf4j
public class HelpCommand extends Command {

    /**
     * The help command sends avaliable help about any given command or sends help on all commands at once.
     */
    public HelpCommand() {
        name = "help";
        description = "Sends a link to the command wiki page";
        usage.add("help");
        usage.add("help {command}");
        aliases = new String[]{"commands"};
    }

    /**
     * Check to see if the command has arguments then send off to one of the other methods.
     * @param event         The CommandEvent class
     */
    @Override
    public void onCommand(CommandEvent event) {

        String[] args = event.getArgs();

        if (event.getArgs().length == 0) {
            sendHelp(event);
        } else {
            sendCommandHelp(event, args[0]);
        }
    }

    /**
     * Generic send help command
     */
    private void sendHelp(CommandEvent event) {
        event.getClient().sendMessage("All Commands: https://github.com/Scoin0/UsagiBot/wiki/Commands");
    }

    /**
     * Send information about the command given
     * @param commandName   The command to request help from
     */
    private void sendCommandHelp(CommandEvent event, String commandName) {
        Optional<Command> command = event.getClient().getCommands()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(commandName) || c.isCommandFor(commandName))
                .findFirst();

        if (command.isPresent() && !command.get().getName().equalsIgnoreCase("unassigned")) {
            event.getClient().sendMessage(command.get().getDescription() + " | Usage: " + command.get().getUsage());
        } else {
            event.getClient().sendMessage("Command not found or does not exist.");
        }
    }
}
