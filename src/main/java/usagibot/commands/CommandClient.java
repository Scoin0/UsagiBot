package usagibot.commands;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventUser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import usagibot.UsagiBot;
import usagibot.commands.twitchcommands.RequestToggleCommand;
import usagibot.osu.api.Beatmap;
import usagibot.osu.api.GameMode;
import usagibot.osu.api.Mods;
import usagibot.osu.api.User;
import usagibot.twitch.TwitchClient;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Slf4j
public class CommandClient {

    private final String prefix = UsagiBot.getConfig().getPrefix();
    private final String channel = UsagiBot.getConfig().getTwitchChannel();
    private Beatmap beatmap;
    private final User user = UsagiBot.getClient().getUser(UsagiBot.getConfig().getOsuUsername(), GameMode.OSU);
    private final ArrayList<Command> commands;
    private final HashMap<String, Integer> commandIndex;
    private String hackyMods = "0";

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
     * @param message   The message to send along with the IRCMessage
     */
    public void sendIRCMessage(EventUser user, Beatmap beatmap, String message, int mods) {
        UsagiBot.getIrcBot().getUserChannelDao().getUser(UsagiBot.getConfig().getBanchoUsername()).send().message(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuIrcMessage() + message, beatmap, user, mods));
    }

    /**
     * Parses the beatmap link sent in Twitch
     * @param message   The beatmap link that was sent
     * @return          The beatmap url
     */
    public String parseMessage(String message) {
        String delimiters = "https?://osu.ppy.sh/(beatmapsets)/([0-9]*)(#osu|#taiko|#ctb|#mania)/([0-9]*)";
        Pattern urlPattern = Pattern.compile(delimiters, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = urlPattern.matcher(message);
        List<String> urlSplit = new ArrayList<>();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                urlSplit.add(matcher.group(i));
            }
        }

        switch (urlSplit.size()) {
            case 1:
            case 2:
            case 3:
                break;
            case 4:
                return urlSplit.get(3);
            default:
                log.warn("Invalid beatmap link sent.");
        }
        return null;
    }

    /**
     * Checks first to see if the sent beatmap is above the starlimit then check to see if
     * requests are on. If so, send the beatmap to the streamer. If not, send the error message.
     * @param event             The person who sent the map
     * @param beatmapToReceive  The URL of the map sent.
     */
    public void receiveBeatmap(EventUser event, String beatmapToReceive) {
        if (RequestToggleCommand.requestToggle && !UsagiBot.getBannedUsers().bannedUsers.contains(event.getName())) {
            log.info("Received possible osu song request. Parsing now...");
            beatmap = UsagiBot.getClient().getBeatmap(parseMessage(beatmapToReceive));
            log.info("Beatmap ID Found: " + beatmap.getId());
            if (beatmapToReceive.contains("+")) {
                String[] splitter = beatmapToReceive.split("\\+");
                String mods = splitter[1];
                // Fuck me, I wanna rewrite all of this.
                hackyMods = mods;
                Optional<Long> aLong = Mods.fromShortNamesContinuous(mods);
                assert Objects.requireNonNull(aLong).isPresent();
                String hackyFix = Mods.toShortNamesContinuous(Mods.getMods((aLong).get()));
                if (beatmap.getDifficulty_rating() > UsagiBot.getConfig().getOsuStarLimit()) {
                    sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuStarLimitMessage(), beatmap, event, Mods.convertToInt(mods)));
                } else {
                    sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getTwitchMessage() + " +" + hackyFix, beatmap, event, Mods.convertToInt(mods)));
                    sendIRCMessage(event, beatmap, " +" + hackyFix, Mods.convertToInt(mods));
                }
            } else {
                if (beatmap.getDifficulty_rating() > UsagiBot.getConfig().getOsuStarLimit()) {
                    sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getOsuStarLimitMessage(), beatmap, event, 0));
                } else {
                    sendMessage(UsagiBot.getConfig().getAPIParsedMessage(UsagiBot.getConfig().getTwitchMessage(), beatmap, event, 0));
                    sendIRCMessage(event, beatmap, "", 0);
                }
            }
        } else {
            sendMessage("You cannot request a beatmap at this time.");
        }
    }

    /**
     * Fires when a chat message is sent in Twitch chat
     * @param event The chat event
     */
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

        // Do not listen to self. Probably not needed, but just in case.
        if (event.getUser().getName().equals("RNRPBot")) return;

        String[] parts = null;
        String rawContent = event.getMessage();

        // Receive, parse, and send beatmap to Osu! and Twitch Chat
        if (event.getMessage().contains("https://osu.ppy.sh/")) {
            receiveBeatmap(event.getUser(), event.getMessage());
        }

        if (rawContent.startsWith(getPrefix()))
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
            }
        }
    }
}
