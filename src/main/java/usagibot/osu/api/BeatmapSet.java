package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.sql.Timestamp;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Represents a beatmapset. This extends BeatmapsetCompact with additional attributes.
 * URL:         https://osu.ppy.sh/docs/index.html?bash#beatmapset
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapSet extends BeatmapSetCompact {

    @JsonProperty("bpm")
    private float bpm;
    @JsonProperty("can_be_hyped")
    private boolean can_be_hyped;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("discussion_enabled")
    private boolean discussion_enabled;
    @JsonProperty("discussion_locked")
    private boolean discussion_locked;
    @JsonProperty("is_scorable")
    private boolean is_scorable;
    @JsonProperty("last_updated")
    private Timestamp last_updated;
    @JsonProperty("ranked_date")
    private Timestamp ranked_date;
    @JsonProperty("source")
    private String source;
    @JsonProperty("storyboard")
    private boolean storyboard;
    @JsonProperty("tags")
    private String tags;

}
