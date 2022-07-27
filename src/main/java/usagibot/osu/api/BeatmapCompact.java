package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Represents a beatmap.
 * URL:         https://osu.ppy.sh/docs/index.html#beatmapcompact
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapCompact {

    @JsonProperty("beatmapset_id")
    private int beatmapset_id;
    @JsonProperty("difficulty_rating")
    private float difficulty_rating;
    @JsonProperty("id")
    private int id;
    @JsonProperty("mode")
    private GameMode mode;
    @JsonProperty("status")
    private RankedStatus status;
    @JsonProperty("total_length")
    private int total_length;
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("version")
    private String version;

    // Optional Attributes
    @JsonProperty("beatmapset")
    private BeatmapSet beatmapset;
    @JsonProperty("checksum")
    private String checksum;
    @JsonProperty("failtimes")
    private FailTimes failtimes;
    @JsonProperty("max_combo")
    private int max_combo;

    /**
     * All fields are optional but there's always at least one field returned.
     * URL: https://osu.ppy.sh/docs/index.html#beatmapcompact-failtimes
     */
    public static class FailTimes {

        // Array of length 100.
        private int[] exit;
        // Array of length 100.
        private int[] fail;

        public int[] getExit() {
            return exit;
        }

        public int[] getFail() {
            return fail;
        }
    }
}
