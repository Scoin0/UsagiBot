package usagibot.osu.api.v2;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a beatmapset. This extends {@link Beatmapset} with additional attributes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapsetExtended extends Beatmapset {

    private Availability availability;
    private float bpm;
    @JsonProperty("can_be_hyped")
    private boolean canBeHyped;
    @JsonProperty("deleted_at")
    private OffsetDateTime deletedAt;
    /**
     * Deprecated, all beatmapsets now have discussion enabled.
     */
    @JsonProperty("discussion_enabled")
    private boolean isDiscussionEnabled;
    @JsonProperty("discussion_locked")
    private boolean isDiscussionLocked;
    private Hype hype;
    @JsonProperty("is_scoreable")
    private boolean isScoreable;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
    @JsonProperty("legacy_thread_url")
    private String legacyThreadUrl;
    @JsonProperty("nominations_summary")
    private NominationsSummary nominationsSummary;
    /**
     * See {@link RankStatus Rank status} for list of possible values.
     */
    private int ranked;
    @JsonProperty("ranked_date")
    private OffsetDateTime rankedDate;
    private float rating;
    private String source;
    @JsonProperty("storyboard")
    private boolean hasStoryboard;
    @JsonProperty("submittedDate")
    private OffsetDateTime submittedDate;
    private String tags;
    @JsonProperty("has_favourited")
    private boolean hasFavourited;

}