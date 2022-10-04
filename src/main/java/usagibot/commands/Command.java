package usagibot.commands;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Slf4j
public abstract class Command {

    protected String name = null;
    protected String description = "No Description Available";
    protected ArrayList<String> usage = new ArrayList<>();
    protected String[] aliases = new String[0];
    protected Command[] subcommands = new Command[0];

    /**
     * The abstract for onCommand. Every command class needs this in order to run
     * @param event         The CommandEvent class
     */
    public abstract void onCommand(CommandEvent event);

    /**
     * The part that actually runs the command.
     * @param event The CommandEvent
     */
    public final void run(CommandEvent event) {

        if (event.getArgs().length > 0) {
            String[] args = event.getArgs();
            for (Command command : subcommands) {
                if (command.isCommandFor(args[0])) {
                    event.setArgs(args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0]);
                    command.run(event);
                }
            }
        }

        try {
            onCommand(event);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Checks if a command is an alias.
     * @param input Alias
     * @return  True or False depending on if it's an alias or not.
     */
    public boolean isCommandFor(String input) {
        if (name.equalsIgnoreCase(input)) return true;

        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
