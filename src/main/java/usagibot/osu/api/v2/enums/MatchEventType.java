package usagibot.osu.api.v2.enums;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

@Getter
public enum MatchEventType {

    HOST_CHANGED("host-changed"),
    MATCH_CREATED("match-created"),
    MATCH_DISBANDED("match-disbanded"),
    OTHER("other"),
    PLAYER_JOINED("player-joined"),
    PLAYER_KICKED("player-kicked"),
    PLAYER_LEFT("player-left");

    @JsonValue
    private final String name;

    MatchEventType(String name) {
        this.name = name;
    }

    @JsonCreator
    public static MatchEventType fromValue(String value) {
        for (MatchEventType  type : values()) {
            if (type.name.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown MatchEventType : " + value);
    }
}