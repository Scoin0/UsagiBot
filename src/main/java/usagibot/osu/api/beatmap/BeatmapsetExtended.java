package usagibot.osu.api.beatmap;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.enums.RankStatus;
import usagibot.osu.api.beatmap.helpers.Availability;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmapset. This extends {@link Beatmapset} with additional values.
 * @see <a href=https://osu.ppy.sh/docs/#beatmapsetextended>#beatmapsetextended</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapsetExtended extends Beatmapset {

    private Availability availability;
    private float bpm;
    @JsonProperty("deleted_at")
    private OffsetDateTime deletedAt;
    @JsonProperty("is_scorable")
    private boolean isScorable;
    @JsonProperty("legacy_thread_url")
    private String legacyThreadUrl;
    /** See {@link RankStatus} for a list of possible values. */
    private int ranked;
    @JsonProperty("ranked_date")
    private OffsetDateTime rankedDate;
    private float rating;
    private String source;
    private boolean storyboard;
    @JsonProperty("submitted_date")
    private OffsetDateTime submittedDate;
    private String tags;

}