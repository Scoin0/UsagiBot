package usagibot.osu.api.v2.enums;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The possible values are denoted either as integer or string.
 */
@Getter
public enum RankStatus {

    GRAVEYARD(-2, "graveyard"),
    WIP(-1, "wip"),
    PENDING(0, "pending"),
    RANKED(1, "ranked"),
    APPROVED(2, "approved"),
    QUALIFIED(3, "qualified"),
    LOVED(4, "loved");

    private final int code;
    @JsonValue
    private final String name;

    RankStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static RankStatus fromValue(Object value) {
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            for (RankStatus status : values()) {
                if (status.code == intValue) return status;
            }
        } else if (value instanceof String) {
            String stringValue = ((String) value).toLowerCase();
            for (RankStatus status : values()) {
                if (status.name.equals(stringValue)) return status;
            }
        }
        throw new IllegalArgumentException("Unknown RankStatus: " + value);
    }
}