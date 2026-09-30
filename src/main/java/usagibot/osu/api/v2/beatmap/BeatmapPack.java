package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import java.util.List;
import java.time.OffsetDateTime;
import usagibot.osu.api.v2.enums.BeatmapPackType;
import usagibot.osu.api.v2.user.UserCompletionData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmap pack.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapPack {

    private String author;
    private OffsetDateTime date;
    private String name;
    /**
     * Whether difficulty reduction mods may be used to clear the pack.
     */
    @JsonProperty("no_diff_reduction")
    private boolean noDiffReduction;
    @JsonProperty("ruleset_id")
    private int rulesetId;
    /**
     * The tag of the beatmap pack. Starts with a character representing the type
     * (See the {@code Tag} column of {@link BeatmapPackType}) followed by an integer.
     */
    private String tag;
    /**
     * The download url of the beatmap pack.
     */
    private String url;

    // Optional Attributes
    private List<Beatmapset> beatmapset;
    @JsonProperty("user_completion_data")
    private UserCompletionData userCompletionData;

}