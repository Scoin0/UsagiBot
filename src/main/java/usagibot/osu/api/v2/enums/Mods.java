package usagibot.osu.api.v2.enums;

import java.util.Set;
import lombok.Getter;
import java.util.EnumSet;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Bitwise enum representing a combination of enabled mods.
 */
@Getter
public enum Mods {

    NO_FAIL      (1, "NF"),
    EASY         (2, "EZ"),
    TOUCH_DEVICE (4, ""),
    HIDDEN       (8, "HD"),
    HARD_ROCK    (16, "HR"),
    SUDDEN_DEATH (32, "SD"),
    DOUBLE_TIME  (64, "DT"),
    RELAX        (128, "RL"),
    HALF_TIME    (256, "HT"),
    /** Only set along with DoubleTime. i.e: NC only gives 576. */
    NIGHTCORE    (512, "NC"),
    FLASHLIGHT   (1024, "FL"),
    AUTOPLAY     (2048, "AT"),
    SPUN_OUT     (4096, "SO"),
    AUTO_PILOT   (8192, "AP"),
    /** Only set along with SuddenDeath. i.e: PF only gives 16416. */
    PERFECT      (16384, "PF"),
    KEY4         (32768, ""),
    KEY5         (65536, ""),
    KEY6         (131072, ""),
    KEY7         (262144, ""),
    KEY8         (524288, ""),
    FADE_IN      (1048576, ""),
    RANDOM       (2097152, ""),
    CINEMA       (4194304, ""),
    TARGET       (8388608, ""),
    KEY9         (16777216, ""),
    KEY_COOP     (33554432, ""),
    KEY1         (67108864, ""),
    KEY3         (134217728, ""),
    KEY2         (268435456, ""),
    SCORE_V2     (536870912, "SV2"),
    MIRROR       (1073741824, "");

    @JsonValue
    private final int bit;
    private final String acronym;

    Mods(int bit, String acronym) {
        this.bit = bit;
        this.acronym = acronym;
    }

    public static Set<Mods> fromBitmask(int bitmask) {
        Set<Mods> mods = EnumSet.noneOf(Mods.class);
        for (Mods mod : Mods.values()) {
            if ((bitmask & mod.bit) != 0) {
                mods.add(mod);
            }
        }
        return mods;
    }

    public static int toBitmask (Set<Mods> mods) {
        int bitmask = 0;
        for (Mods mod : mods) {
            bitmask |= mod.bit;
        }
        return bitmask;
    }

    public static Mods fromAcronym(String modString) {
        for (Mods mod : values()) {
            if (mod.acronym.equalsIgnoreCase(modString)) {
                return mod;
            }
        }
        throw new IllegalArgumentException("Unknown mod acronym: " + modString);
    }

    public static String toAcronymString(Set<Mods> mods) {
        Set<Mods> displayedMods = EnumSet.noneOf(Mods.class);

        if (mods == null || mods.isEmpty()) {
            return "";
        }

        if (displayedMods.contains(Mods.NIGHTCORE)) {
            displayedMods.remove(Mods.DOUBLE_TIME);
        }
        if (displayedMods.contains(Mods.PERFECT)) {
            displayedMods.remove(Mods.SUDDEN_DEATH);
        }

        StringBuilder sb = new StringBuilder("+");
        mods.stream()
                .sorted(Comparator.comparingInt(a -> a.bit))
                .forEach(mod -> sb.append(mod.acronym));
        return sb.toString();
    }

    public static Set<Mods> fromAcronymString(String modString) {
        Set<Mods> result = EnumSet.noneOf(Mods.class);

        if (modString == null || modString.isEmpty()) {
            return result;
        }

        String input = modString.startsWith("+") ? modString.substring(1) : modString;
        while(!input.isEmpty()) {
            Mods matched = null;
            for (Mods mod : Mods.values()) {
                String acronym = mod.acronym;
                if (acronym.isEmpty()) continue;
                if (input.startsWith(mod.acronym)) {
                    matched = mod;
                    break;
                }
            }

            if (matched == null) {
                throw new IllegalArgumentException("Unknown mod acronym: " + input);
            }

            result.add(matched);
            input = input.substring(matched.acronym.length());
        }
        return result;
    }

    // Possibly move to a utils class?
    public static int convertTime(int time, int modsBitmask) {
        double speed = getSpeedModifier(modsBitmask);
        return (int) (time / speed);
    }

    public static double convertBPM(double bpm, int modsBitmask) {
        return bpm * getSpeedModifier(modsBitmask);
    }

    public static double getSpeedModifier(int modsBitmask) {
        Set<Mods> mods = Mods.fromBitmask(modsBitmask);
        if (mods.contains(Mods.HALF_TIME)) return 0.75;
        if (mods.contains(Mods.NIGHTCORE) || mods.contains(Mods.DOUBLE_TIME)) return 1.5;
        return 1.0;
    }
}