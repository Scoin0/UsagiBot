package usagibot.osu.api.beatmap;

import lombok.Getter;
import usagibot.osu.api.enums.Ruleset;
import usagibot.osu.api.enums.RankStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent a Beatmap
 * @see <a href=https://osu.ppy.sh/docs/#Beatmap>#beatmap</a>
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
    /** See {@link RankStatus Rank Status} for a list of possible values. */
    private String status;
    @JsonProperty("total_length")
    private int totalLength;
    @JsonProperty("user_id")
    private int userId;
    private String version;

    // Optional
    private BeatmapsetExtended beatmapset;
    @JsonProperty("current_user_playcount")
    private int currentUserPlaycount;
    @JsonProperty("max_combo")
    private int maxCombo;

}