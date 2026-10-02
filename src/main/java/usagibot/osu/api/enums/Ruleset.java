package usagibot.osu.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Available rulesets:
 * @see <a href=https://osu.ppy.sh/docs/#ruleset>#ruleset</a>
 */

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
    public static Ruleset fromString(String name) {
        for (Ruleset ruleset : values()) {
            if (ruleset.name.equalsIgnoreCase(name)) {
                return ruleset;
            }
        }
        throw new IllegalArgumentException("Unknown Ruleset " + name);
    }
}