package usagibot.osu.api.v2.chat;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.v2.user.User;
import usagibot.osu.api.v2.enums.ChannelType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents an individual chat "channel" in the game.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ChatChannel {

    @JsonProperty("channel_id")
    private int channelId;
    private String name;
    private String description;
    /**
     * Display icon for the channel.
     */
    private String icon;
    /**
     * Type of channel.
     */
    private ChannelType type;
    @JsonProperty("message_length_limit")
    private int messageLengthLimit;
    /**
     * User can't send messages when the value is {@code true}.
     */
    private boolean moderated;
    /**
     * Value from requests that is relayed back to the sender.
     */
    private String uuid;

    // Optional Attributes
    /**
     * @deprecated Use {@code current_user_attributes.last_read_id}.
     */
    @JsonProperty("last_read_id")
    private int lastReadId;
    /**
     * {@code message_id} of last known message (only return in presence responses).
     */
    @JsonProperty("last_message_id")
    private int lastMessageId;
    /**
     * @deprecated Up to 50 most recent messages.
     */
    @JsonProperty("recent_messages")
    private List<ChatMessage> recentMessages;
    /**
     * Array of {@code user_id} that are in the channel (not included for {@code PUBLIC} channels).
     */
    private List<User> users;

}