package usagibot.osu.api.enums;

import lombok.Getter;
import java.util.Map;
import java.util.Locale;
import java.util.HashMap;
import lombok.AccessLevel;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Available rulesets:
 * @see <a href=https://osu.ppy.sh/docs/#ruleset>#ruleset</a>
 */

@Getter
public enum Ruleset {

    OSU("osu", 0, "std", "standard"),
    TAIKO("taiko", 1),
    FRUITS("fruits", 2, "catch", "ctb"),
    MANIA("mania", 3);

    private static final Map<String, Ruleset> lookup = new HashMap<>();

    static {
        for (Ruleset ruleset : Ruleset.values()) {
            lookup.put(ruleset.name, ruleset);
            for (String alias : ruleset.aliases) {
                lookup.put(alias, ruleset);
            }
        }
    }

    @JsonValue
    private final String name;
    private final int id;
    @Getter(AccessLevel.NONE)
    private final String[] aliases;

    Ruleset(String name, int id, String... aliases) {
        this.name = name;
        this.id = id;
        this.aliases = aliases;
    }

    public static Optional<Ruleset> parse(String input) {
        if (input == null) return Optional.empty();
        return Optional.ofNullable(lookup.get(input.trim().toLowerCase(Locale.ROOT)));
    }

    @JsonCreator
    public static Ruleset fromString(String name) {
        return parse(name).orElseThrow(() -> new IllegalArgumentException("Invalid ruleset name: " + name));
    }
}