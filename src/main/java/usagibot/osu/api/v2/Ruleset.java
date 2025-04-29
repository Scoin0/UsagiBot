package usagibot.osu.api.v2;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Available rulesets: {@code osu, taiko, fruits, mania}.
 */
@Getter
public enum Ruleset {

    OSU("osu"),
    TAIKO("taiko"),
    FRUITS("fruits"),
    MANIA("mania");

    @JsonValue
    private final String name;

    Ruleset(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Ruleset fromValue(String value) {
        for (Ruleset ruleset : values()) {
            if (ruleset.name.equalsIgnoreCase(value)) {
                return ruleset;
            }
        }
        throw new IllegalArgumentException("Unknown ruleset: " + value);
    }
}