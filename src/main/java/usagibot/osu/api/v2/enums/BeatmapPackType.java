package usagibot.osu.api.v2.enums;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

@Getter
public enum BeatmapPackType {

    /** Standard */
    STANDARD("standard", "S"),
    /** Featured Artist */
    FEATURED("featured", "F"),
    /** Tournament */
    TOURNAMENT("tournament", "P"),
    /** Project Loved */
    LOVED("loved","L"),
    /** Spotlights */
    CHART("chart", "R"),
    /** Theme */
    THEME("theme", "T"),
    /** Artist/Album */
    ARTIST("artist", "A");

    @JsonValue
    private final String name;
    private final String tag;

    BeatmapPackType(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    @JsonCreator
    public static BeatmapPackType fromValue(String value) {
        for (BeatmapPackType type : values()) {
            if (type.name.equalsIgnoreCase(value) || type.tag.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown BeatmapPackType: " + value);
    }

    public static BeatmapPackType fromTagPrefix(String tag) {
        if (tag == null || tag.isEmpty()) {
            throw new IllegalArgumentException("Tag is null or empty");
        }

        char prefix = Character.toUpperCase(tag.charAt(0));
        for (BeatmapPackType type : values()) {
            if (type.tag.charAt(0) == prefix) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown BeatmapPackType prefix in tag: " + tag);
    }
}