package usagibot.osu.beatmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 *
 * Represents a Beatmap
 * osu.ppy.sh/docs/#Beatmap
 * Sep 30th, 2026
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Beatmap {

    @JsonProperty("beatmapset_id")
    private int beatmapsetId;
    @JsonProperty("difficulty_rating")
    private float difficultyRating;
    private int id;

    // @JsonProperty("mode")

    private String status;
    @JsonProperty("total_length")
    private int totalLength;
    @JsonProperty("user_id")
    private int userId;
    private String version;




}
