package usagibot.osu.api.enums;

import java.util.*;

public enum StableMod {

    NoFail      (1,          "NF"),
    Easy        (2,          "EZ"),
    TouchDevice (4,          "TD"),
    Hidden      (8,          "HD"),
    HardRock    (16,         "HR"),
    SuddenDeath (32,         "SD"),
    DoubleTime  (64,         "DT"),
    Relax       (128,        "RX"),
    HalfTime    (256,        "HT"),
    Nightcore   (512,        "NC"), // always set together with DoubleTime (576)
    Flashlight  (1024,       "FL"),
    Autoplay    (2048,       "AT"),
    SpunOut     (4096,       "SO"),
    Relax2      (8192,       "AP"),
    Perfect     (16384,      "PF"), // always set together with SuddenDeath (16416)
    Key4        (32768,      "4K"),
    Key5        (65536,      "5K"),
    Key6        (131072,     "6K"),
    Key7        (262144,     "7K"),
    Key8        (524288,     "8K"),
    FadeIn      (1048576,    "FI"),
    Random      (2097152,    "RD"),
    Cinema      (4194304,    "CN"),
    Target      (8388608,    "TP"),
    Key9        (16777216,   "9K"),
    KeyCoop     (33554432,   "DS"),
    Key1        (67108864,   "1K"),
    Key3        (134217728,  "3K"),
    Key2        (268435456,  "2K"),
    ScoreV2     (536870912,  "SV2"),
    Mirror      (1073741824, "MR");

    private static final Map<String, StableMod> byAcronym = new HashMap<>();

    static {
        for (StableMod mod : values()) {
            byAcronym.put(mod.acronym, mod);
        }
        byAcronym.put("RL", Relax);
    }

    private final int bit;
    private final String acronym;

    StableMod(int bit, String acronym) {
        this.bit = bit;
        this.acronym = acronym;
    }

    public int bit() {
        return bit;
    }

    public String acronym() {
        return acronym;
    }

    public int impliedBits() {
        return switch (this) {
            case Nightcore -> bit | DoubleTime.bit;
            case Perfect -> bit | SuddenDeath.bit;
            default -> bit;
        };
    }

    public static Optional<StableMod> fromAcronym(String acronym) {
        return Optional.ofNullable(byAcronym.get(acronym));
    }

    public static List<StableMod> fromBits(int bits) {
        boolean nightcore = (bits & Nightcore.bit) != 0;
        boolean perfect = (bits & Perfect.bit) != 0;

        List<StableMod> result = new ArrayList<>();
        for (StableMod mod : values()) {
            if (mod == DoubleTime && nightcore) continue;
            if (mod == SuddenDeath && perfect) continue;
            if ((bits & mod.bit) != 0) {
                result.add(mod);
            }
        }
        return result;
    }

    public static int toBits(Collection<StableMod> mods) {
        int bits = 0;
        for (StableMod mod : mods) {
            bits |= mod.impliedBits();
        }
        return bits;
    }
}