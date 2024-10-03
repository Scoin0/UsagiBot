package usagibot.commands.twitchcommands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.Command;
import usagibot.commands.CommandClient;
import usagibot.commands.CommandEvent;
import usagibot.commands.DynamicCommand;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class RemoveCommand extends Command {

    private static final String commandFile = "custom_commands.json";
    private HashMap<String, DynamicCommand> customCommands;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommandClient commandClient;

    public RemoveCommand(CommandClient commandClient) {
        name = "removecommand";
        description = "remove a custom command with 1 output string. (Streamer Only)";
        usage.add("removecommand");
        usage.add("removecommand <command name>");
        usage.add("remove");
        usage.add("remove <command name>");
        aliases = new String[]{"remove", "rm"};
        this.commandClient = commandClient;
    }

    @Override
    public void onCommand(CommandEvent event) {
        if (!event.getEvent().getUser().getName().equals(UsagiBot.getConfig().getTwitchChannel())) {
            return; // Ignore commands from other users, Streamer Only
        }

        if (event.getArgs().length < 1) {
            event.getClient().sendMessage("Usage: " + getUsage());
            return;
        }

        loadCommands();
        String commandName = event.getArgs()[0];

        if (!customCommands.containsKey(commandName)) {
            event.getClient().sendMessage("Command !" + commandName + "does not exist.");
            return;
        }

        DynamicCommand customCommand = customCommands.get(commandName);
        commandClient.removeCommand(customCommand);
        customCommands.remove(commandName);
        saveCommands();
        event.getClient().sendMessage("Command !" + commandName + " removed.");
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
            } catch (IOException e) {
                log.warn("Error loading commands: {}", e.getMessage());
            }
        } else {
            customCommands = new HashMap<>();
        }
    }
}