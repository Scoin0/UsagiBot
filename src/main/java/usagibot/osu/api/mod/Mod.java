package usagibot.osu.api.mod;

import java.util.Map;
import java.util.Locale;
import java.util.Optional;
import usagibot.osu.api.enums.StableMod;

public record Mod(String acronym, Map<String, Object> settings) {

    public Mod {
        acronym = acronym.toUpperCase(Locale.ROOT);
        settings = settings == null ? Map.of() : Map.copyOf(settings);
    }

    public static Mod of(String acronym) {
        return new Mod(acronym, Map.of());
    }

    public static Mod of(String acronym, Map<String, Object> settings) {
        return new Mod(acronym, settings);
    }

    public Optional<StableMod> stable() {
        return StableMod.fromAcronym(acronym);
    }

    public double setting(String key, double fallback) {
        return settings.get(key) instanceof Number n ? n.doubleValue() : fallback;
    }
}