package usagibot.commands.twitchcommands;

import java.io.File;
import java.util.Arrays;
import usagibot.UsagiBot;
import java.util.HashMap;
import java.io.IOException;
import usagibot.commands.Command;
import lombok.extern.slf4j.Slf4j;
import usagibot.commands.CommandEvent;
import usagibot.commands.CommandClient;
import usagibot.commands.DynamicCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;

@Slf4j
public class AddCommand extends Command {

    private static final String commandFile = "custom_commands.json";
    private HashMap<String, DynamicCommand> customCommands;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommandClient commandClient;

    public AddCommand(CommandClient commandClient) {
        name = "addcommand";
        description = "Adds a custom command with 1 output string. (Streamer Only)";
        usage.add("addcommand");
        usage.add("addcommand <command name> <output>");
        usage.add("add");
        usage.add("add <command name> <output>");
        aliases = new String[]{"add"};
        this.commandClient = commandClient;
        loadCommands();
    }

    @Override
    public void onCommand(CommandEvent event) {
        if (!event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            return; // Ignore commands from other users, Streamer Only
        }

        if (event.getArgs().length < 2) {
            event.getClient().sendMessage("Usage: " + getUsage());
            return;
        }

        String commandName = event.getArgs()[0];
        String output = String.join(" ", Arrays.copyOfRange(event.getArgs(), 1, event.getArgs().length));

        DynamicCommand customCommand = new DynamicCommand(commandName, output);
        commandClient.addCommand(customCommand);

        customCommands.put(commandName, customCommand);
        saveCommands();
        event.getClient().sendMessage("Command !" + commandName + " added.");
    }

    private void saveCommands() {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(commandFile), customCommands);
        } catch (IOException e) {
            log.info("Error saving commands: {}", e.getMessage());
        }
    }

    private void loadCommands() {
        File file = new File(commandFile);
        if (file.exists()) {
            try {
                customCommands = objectMapper.readValue(file, new TypeReference<HashMap<String, DynamicCommand>>() {});
                for (DynamicCommand command : customCommands.values()) {
                    commandClient.addCommand(command);
                }
            } catch (IOException e) {
                log.warn("Error loading commands: {}", e.getMessage());
            }
        } else {
            customCommands = new HashMap<>();
        }
    }
}