package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.v2.enums.RankStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent a beatmap. This extends {@link Beatmap} with additional attributes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapExtended extends Beatmap {

    private float accuracy;
    private float ar;
    @JsonProperty("beatmapset_id")
    private int beatmapsetId;
    @JsonProperty("bpm")
    private Float bpm;
    private boolean convert;
    @JsonProperty("count_circles")
    private int countCircles;
    @JsonProperty("count_sliders")
    private int countSliders;
    @JsonProperty("count_spinners")
    private int countSpinners;
    private float cs;
    @JsonProperty("deleted_at")
    private OffsetDateTime deletedAt;
    private float drain;
    @JsonProperty("hit_length")
    private int hitLength;
    @JsonProperty("is_scoreable")
    private boolean isScoreable;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
    @JsonProperty("mode_int")
    private int modeInt;
    private int passcount;
    /**
     * See {@link RankStatus Rank Status} for a list of possible values.
     */
    private int ranked;
    private String url;

}