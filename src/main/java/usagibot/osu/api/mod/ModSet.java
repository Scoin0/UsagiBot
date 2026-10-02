package usagibot.osu.api.mod;

import java.util.*;
import java.util.stream.Collectors;
import usagibot.osu.api.enums.StableMod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public record ModSet(List<Mod> mods) {

    public static final ModSet NONE = new ModSet(List.of());

    public ModSet {
        LinkedHashMap<String, Mod> unique = new LinkedHashMap<>();
        for (Mod mod : mods) {
            unique.putIfAbsent(mod.acronym(), mod);
        }
        if (unique.containsKey("NC")) unique.remove("DT");
        if (unique.containsKey("PF")) unique.remove("SD");
        mods = List.copyOf(unique.values());
    }

    public static ModSet of(Mod... mods) {
        return new ModSet(List.of(mods));
    }

    public static ModSet ofAcronyms(String... acronyms) {
        List<Mod> list = new ArrayList<>();
        for (String acronym : acronyms) list.add(Mod.of(acronym));
        return new ModSet(list);
    }

    public static ModSet fromStable(int bits) {
        List<Mod> list = new ArrayList<>();
        for (StableMod stable : StableMod.fromBits(bits)) {
            list.add(Mod.of(stable.acronym()));
        }
        return new ModSet(list);
    }

    public static Optional<ModSet> parse(String text) {
        if (text == null) return Optional.empty();
        String s = text.toUpperCase(Locale.ROOT).replaceAll("[+,\\s]", "");

        if (s.isEmpty() || s.equals("NM")) return Optional.of(NONE);
        if (s.matches("\\d+")) {
            try {
                return Optional.of(fromStable(Integer.parseInt(s)));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }

        List<Mod> list = new ArrayList<>();
        for (int i = 0; i < s.length(); ) {
            int length = s.startsWith("SV2", i) ? 3 : 2;
            if (i + length > s.length()) return Optional.empty();
            String acronym = s.substring(i, i + length);
            list.add(Mod.of(acronym.equals("RL") ? "RX" : acronym));
            i += length;
        }
        return Optional.of(new ModSet(list));
    }

    public static ModSet fromStableMods(Collection<StableMod> stable) {
        return fromStable(StableMod.toBits(stable));
    }

    public boolean isEmpty() {
        return mods.isEmpty();
    }

    public boolean has(String acronym) {
        String wanted = acronym.toUpperCase(Locale.ROOT);
        return mods.stream().anyMatch(mod -> mod.acronym().equals(wanted));
    }

    public boolean isStableCompatible() {
        return mods.stream().allMatch(m -> m.stable().isPresent() && m.settings().isEmpty());
    }

    public OptionalInt toStableBits() {
        if (!isStableCompatible()) return OptionalInt.empty();
        int bits = 0;
        for (Mod mod : mods) {
            bits |= mod.stable().orElseThrow().impliedBits();
        }
        return OptionalInt.of(bits);
    }

    public double speedMultiplier() {
        for (Mod mod : mods) {
            switch (mod.acronym()) {
                case "DT", "NC" -> { return mod.setting("speed_change", 1.5); }
                case "HT", "DC" -> { return mod.setting("speed_change", 0.75); }
                default -> { }
            }
        }
        return 1.0;
    }

    public double applyToBpm(double bpm) {
        return bpm * speedMultiplier();
    }

    public int applyToLength(int millis) {
        return (int) Math.round(millis / speedMultiplier());
    }

    public ArrayNode toJson(ObjectMapper mapper) {
        ArrayNode array = mapper.createArrayNode();
        boolean usedObjects = mods.stream().anyMatch(mod -> !mod.settings().isEmpty());
        for (Mod mod : mods) {
            if (!usedObjects) {
                array.add(mod.acronym());
                continue;
            }
            ObjectNode node = array.addObject().put("acronym", mod.acronym());
            if (!mod.settings().isEmpty()) {
                node.set("settings", mapper.valueToTree(mod.settings()));
            }
        }
        return array;
    }

    @Override
    public String toString() {
        return mods.isEmpty() ? "NM" : mods.stream().map(Mod::acronym).collect(Collectors.joining());
    }
}