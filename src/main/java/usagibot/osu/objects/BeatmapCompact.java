package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapCompact {

    // Represent a Beatmap
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

    public static class FailTimes {

        private int[] exit;
        private int[] fail;

        public int[] getExit() {
            return exit;
        }

        public int[] getFail() {
            return fail;
        }
    }
}
