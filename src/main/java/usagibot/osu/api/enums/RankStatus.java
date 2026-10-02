package usagibot.osu.api.enums;

import lombok.Getter;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The possible values are denoted either as integer or string.
 * @see <a href=https://osu.ppy.sh/docs/#beatmapset-rank-status>#beatmapset-rank-status</a>
 */

@Slf4j
@Getter
public enum RankStatus {

    UNKNOWN(Integer.MIN_VALUE, "unknown"),
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
        if (value instanceof Integer i) {
            for (RankStatus s : values()) if (s.code == i) return s;
        } else if (value instanceof String str) {
            String lower = str.toLowerCase(Locale.ROOT);
            for (RankStatus s : values()) if (s.name.equals(lower)) return s;
        }
        log.warn("Unknown rank status: {}", value);
        return UNKNOWN;
    }
}