package usagibot.osu.api.v2.enums;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonCreator;

@Getter
public enum ChannelType {

    PUBLIC("PUBLIC"),
    /**
     * Is player in the allowed groups? (channel.allowed.groups)
     */
    PRIVATE("PRIVATE"),
    /**
     * Is player currently in the mp game?
     */
    MULTIPLAYER("MULTIPLAYER"),
    SPECTATOR("SPECTATOR"),
    /**
     * @deprecated
     */
    TEMPORARY("TEMPORARY"),
    /**
     * For PMs, two factors are taken into account: Is either user blocking the other? If so, deny. Does the target only accept PMs from friends? Is the current user a friend? If not, deny.
     */
    PM("PM"),
    /**
     * Is player in channel? (user_channels)
     */
    GROUP("GROUP"),
    /**
     * is User in the {@code announce} group?
     */
    ANNOUNCE("ANNOUNCE");

    private final String name;

    ChannelType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static ChannelType fromValue(String value) {
        for (ChannelType type : values()) {
            if (type.name.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ChannelType: " + value);
    }
}