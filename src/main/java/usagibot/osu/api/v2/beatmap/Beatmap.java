package usagibot.osu.api.v2.beatmap;


import lombok.Getter;
import java.util.List;
import usagibot.osu.api.v2.enums.Ruleset;
import usagibot.osu.api.v2.enums.RankStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmap.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Beatmap {

    @JsonProperty("beatmapset_id")
    private int beatmapsetId;
    @JsonProperty("difficulty_rating")
    private float difficultyRating;
    private int id;
    private Ruleset mode;
    /**
     * See {@link RankStatus Rank Status} for list of possible values.
     */
    private String status;
    @JsonProperty("total_length")
    private int totalLength;
    @JsonProperty("user_id")
    private int userId;
    private String version;

    //Optional
    /**
     * Beatmapset for Beatmap object, BeatmapsetExtended for BeatmapExtended object. null if the beatmap doesn't have associated beatmapset (e.g. deleted).
     */
    private Beatmapset beatmapset;
    private String checksum;
    @JsonProperty("current_user_playcount")
    private int currentUserPlaycount;
    @JsonProperty("failtimes")
    private FailTimes failTimes;
    @JsonProperty("max_combo")
    private int maxCombo;
    /**
     * List of owners (mappers) for the Beatmap.
     */
    private List<BeatmapOwner> beatmapOwners;

}