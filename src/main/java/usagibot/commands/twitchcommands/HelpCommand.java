package usagibot.commands.twitchcommands;

import usagibot.commands.Command;
import usagibot.commands.CommandEvent;

import java.util.Optional;

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

        boolean isCommandFound;
        boolean isAliasCommandFound;
        Optional<Command> command = event.getClient().getCommands().stream().filter(c -> c.getName().equalsIgnoreCase(commandName)).findAny();
        Optional<Command> aliasCommand = event.getClient().getCommands().stream().filter(c -> c.isCommandFor(commandName)).findAny();

        if (command.isPresent()) {
            isCommandFound = !command.get().getName().equalsIgnoreCase("unassigned");
        } else {
            isCommandFound = false;
        }

        if (aliasCommand.isPresent()) {
            isAliasCommandFound = !aliasCommand.get().getName().equalsIgnoreCase("unassigned");
        } else {
            isAliasCommandFound = false;
        }

        if (isCommandFound) {
            event.getClient().sendMessage(command.get().getDescription() + " | Usage: " + command.get().getUsage());
        } else if (isAliasCommandFound) {
            event.getClient().sendMessage(aliasCommand.get().getDescription() + " | Usage: " + aliasCommand.get().getUsage());
        } else {
            event.getClient().sendMessage("Command not found or does not exist.");
        }
    }
}
