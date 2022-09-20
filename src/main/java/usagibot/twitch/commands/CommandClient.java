package usagibot.twitch.commands;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Getter
@Slf4j
public class CommandClient {

    private final OffsetDateTime start;
    private String prefix = UsagiBot.getConfig().getPrefix();
    private final ArrayList<Command> commands;
    private final HashMap<String, Integer> commandIndex;
    private final HashMap<String, OffsetDateTime> cooldowns;
    private final HashMap<String, Integer> disabledUsers;

    public CommandClient(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
        start = OffsetDateTime.now();
        commands = new ArrayList<>();
        commandIndex = new HashMap<>();
        cooldowns = new HashMap<>();
        disabledUsers = new HashMap<>();
    }

    public void addCommand(Command command) {
        String name = command.name;
        synchronized (commandIndex) {
            if (commandIndex.containsKey(name)) throw new IllegalArgumentException("The " + name + " command already has an index!");
            for (String alias : command.getAliases()) {
                if (commandIndex.containsKey(alias)) throw new IllegalArgumentException("The " + name + " command already has an index!");
                commandIndex.put(alias, commands.size());
            }
            commandIndex.put(name, commands.size());
        }
        commands.add(command);
        System.out.println("Added the command " + command.name);
    }

    public void enableUser(String user) {
        if (disabledUsers.containsKey(user)) disabledUsers.remove(user);
    }

    public void disabledUser(String user, int type) {
        if (!disabledUsers.containsKey(user)) disabledUsers.put(user, type);
    }

    public OffsetDateTime getCooldown(String name) {
        return cooldowns.get(name);
    }

    public int getRemainingCooldown(String name) {
        if (cooldowns.containsKey(name)) {
            int time = (int) OffsetDateTime.now().until(cooldowns.get(name), ChronoUnit.SECONDS);
            if (time <= 0) {
                cooldowns.remove(name);
                return 0;
            }
            return time;
        }
        return 0;
    }

    public void applyCooldown(String name, int seconds) {
        cooldowns.put(name, OffsetDateTime.now().plusSeconds(seconds));
    }

    public boolean isUserDisabled(String user) {
        return disabledUsers.containsKey(user) && disabledUsers.get(user) >= 1;
    }

    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

        // Do not listen to self.
        if (event.getUser().getName().equals("RNRPBot")) return;

        String parts[] = null;
        String rawContent = event.getMessage();

        if (parts == null && rawContent.startsWith(getPrefix()))
            parts = Arrays.copyOf(rawContent.substring(getPrefix().length()).trim().split("\\s+", 2), 2);

        if (parts != null) {

            String name = parts[0];
            String[] args = parts[1] == null ? new String[0] : parts[1].split("\\s+");

            final Command command;
            synchronized (commandIndex) {
                int i = commandIndex.getOrDefault(name.toLowerCase(), -1);
                command = i != -1 ? commands.get(i) : null;
            }

            if (command != null) {
                CommandEvent commandEvent = new CommandEvent(event, args, this);
                log.info("Sending " + commandEvent.getClient().getPrefix() +  command.getName() + " command in #" + event.getChannel().getName());
                command.run(commandEvent);
                return;
            }
        }
    }
}
