package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Follows the osu!web documentation (As of September 13th, 2022)
 * Description: Represents a beatmap. This extends BeatmapCompact with additional attributes.
 * URL:         https://github.com/ppy/osu-api/wiki#mods
 * I looked at Tillerino bot for most of this
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public enum Mods {

    NoFail      (1, "NF"),
    Easy        (2, "EZ"),
    TouchDevice (4, null),
    Hidden      (8, "HD"),
    HardRock    (16, "HR"),
    SuddenDeath (32, "SD"),
    DoubleTime  (64, "DT"),
    Relax       (128, "RL"),
    HalfTime    (256, "HT"),
    Nightcore   (512, "NC"), // Set along with DoubleTime (Value should be 576)
    Flashlight  (1024, "FL"),
    Autoplay    (2048, "AT"),
    SpunOut     (4096, "SO"),
    Relax2      (8192, "AP"),
    Perfect     (16384, "PF"), // Set along with SuddenDeath (Value should be 16416)
    Key4        (32768, null),
    Key5        (65536, null),
    Key6        (131072, null),
    Key7        (262144, null),
    Key8        (524288, null),
    FadeIn      (1048576, null),
    Random      (2097152, null),
    Cinema      (4194304, null),
    Target      (8388608, null),
    Key9        (16777216, null),
    KeyCoop     (33554432, null),
    Key1        (67108864, null),
    Key3        (134217728, null),
    Key2        (268435456, null),
    ScoreV2     (536870912, "SV2"),
    Mirror      (1073741824, null);

    final long bit;
    String shortName;
    static HashMap<String, Mods> shortNames = new HashMap<>();

    Mods(long bit, String shortName) {
        this.bit = bit;
        this.shortName = shortName;
    }

    public boolean is(long mods) {
        return (mods & bit) == bit;
    }

    public static LinkedList<Mods> getMods(long mods) {
        LinkedList<Mods> ret = new LinkedList<>();

        Mods[] values = values();

        for (int i = 0; i < values.length; i++) {
            if (values[i].is(mods))
                ret.add(values[i]);
        }
        return ret;
    }

    public static Mods fromShortName(String shortName) {
        prepare();
        return shortNames.get(shortName);
    }

    private static void prepare() {
        if (shortNames.isEmpty()) {
            Mods[] values = values();
            for (int i = 0; i < values.length; i++) {
                if (values[i].shortName == null)
                    continue;
                shortNames.put(values[i].shortName, values[i]);
            }
        }
    }

    public static long getMask(Mods ... mods) {
        return getMask(Arrays.asList(mods));
    }

    public static long add(long mods, Mods modsToAdd) {
        return mods | modsToAdd.bit;
    }

    public static long getMask(Collection<Mods> mods) {
        long ret = 0;
        for(Mods m : mods) {
            ret |= m.bit;
        }
        return  ret;
    }

    public static Long fromShortNamesContinuous(String message) {
        long mods = 0;
        for (int i = 0; i < message.length(); i+=2) {
            try {
                Mods mod = fromShortName(message.substring(i, i + 2).toUpperCase());
                if (mod == null) {
                    return null;
                }
                if (mod == Nightcore) {
                    mods += 576;
                } else if (mod == Perfect) {
                    mods += 16416;
                } else {
                    mods |= getMask(mod);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return mods;
    }

    public static String toShortNamesContinuous(Collection<Mods> mods) {
        StringBuilder ret = new StringBuilder();

        for(Mods mod : mods) {
            ret.append(mod.getShortName());
        }
        return ret.toString();
    }
}
