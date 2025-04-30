package usagibot.osu.api.v2.chat;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.v2.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents an individual Message within a {@link ChatChannel}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ChatMessage {

    /**
     * {@code channel_id} of where the message was sent.
     */
    @JsonProperty("channel_id")
    private int channelId;
    /**
     * Message content.
     */
    private String content;
    /**
     * Was this an action? i.e. {@code /me dances}.
     */
    private boolean isAction;
    /**
     * Unique identifier for message
     */
    @JsonProperty("message_id")
    private int messageId;
    /**
     * {@code user_id} of the sender.
     */
    @JsonProperty("sender_id")
    private int senderId;
    /**
     * When the message was sent, ISO-8601.
     */
    private OffsetDateTime timestamp;
    /**
     * Type of message; {@code action}, {@code markdown}, or {@code plain}.
     */
    private String type;
    /**
     * Message identifier originally sent by client.
     */
    private String uuid;

    // Optional Attributes
    /**
     * Embedded User object to save additional api lookups.
     */
    private User sender;

}