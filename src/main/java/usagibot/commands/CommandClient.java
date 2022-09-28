package usagibot.commands;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.GameMode;
import usagibot.osu.api.User;
import usagibot.twitch.TwitchClient;

import java.text.NumberFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Slf4j
public class CommandClient {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();
    private Beatmap beatmap;
    private User user = UsagiBot.getClient().getUser(UsagiBot.getConfig().getOsuUsername(), GameMode.OSU);
    private final ArrayList<Command> commands;
    private final HashMap<String, Integer> commandIndex;

    /**
     * The CommandClient Constructor
     * @param eventHandler  Twitch4J's Event Handler
     */
    public CommandClient(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
        commands = new ArrayList<>();
        commandIndex = new HashMap<>();
    }

    /**
     * Adds the commands to the index of commands list
     * @param command   The command to be added
     */
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
        log.info("Added the command " + command.name);
    }

    /**
     * Sends a message within Twitch chat
     * @param message   The message to be sent
     */
    public void sendMessage(String message) {
        TwitchClient.client.getChat().sendMessage(channel, message);
    }

    /**
     * Sends a message within Osu
     * @param user      The user that sent the beatmap
     * @param beatmap   The beatmap information
     */
    public void sendIRCMessage(EventUser user, Beatmap beatmap, String message) {
        UsagiBot.getIrcBot().getUserChannelDao().getUser(UsagiBot.getConfig().getBanchoUsername()).send().message(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuIrcMessage(), beatmap, user) + message);
    }

    /**
     * Parses the beatmap link sent in Twitch
     * @param message   The beatmap link that was sent
     * @return          The beatmap url
     */
    public String parseMessage(String message) {
        String delimiters = "https?:\\/\\/osu.ppy.sh\\/(beatmapsets)\\/([0-9]*)(#osu|#taiko|#ctb|#maina)\\/([0-9]*)";
        Pattern urlPattern = Pattern.compile(delimiters, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = urlPattern.matcher(message);
        List<String> urlSplit = new ArrayList<String>();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                urlSplit.add(matcher.group(i));
            }
        }

        switch (urlSplit.size()) {
            case 4:
                String beatmap = urlSplit.get(3);
                return beatmap;
            default:
                log.warn("Invalid beatmap link sent.");
        }
        return null;
    }

    /**
     * Fires when a chat message is sent in Twitch chat
     * @param event The chat event
     */
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
