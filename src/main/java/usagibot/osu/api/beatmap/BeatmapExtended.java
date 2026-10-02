package usagibot.osu.api.beatmap;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.enums.RankStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent a beatmap. This extends {@link Beatmap} with additional attributes.
 * @see <a href=https://osu.ppy.sh/docs/#beatmapextended>#beatmapextended</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapExtended extends Beatmap {

    private float accuracy;
    @JsonProperty("ar")
    private float approachRate;
    @JsonProperty("beatmapset_id")
    private int beatmapsetId;
    private Float bpm;
    private boolean convert;
    @JsonProperty("count_circles")
    private int countCircles;
    @JsonProperty("count_sliders")
    private int countSliders;
    @JsonProperty("count_spinners")
    private int countSpinners;
    @JsonProperty("cs")
    private float circleSize;
    @JsonProperty("deleted_at")
    private OffsetDateTime deletedAt;
    @JsonProperty("hit_length")
    private int hitLength;
    @JsonProperty("is_scorable")
    private boolean isScorable;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
    @JsonProperty("mode_int")
    private int modeInt;
    @JsonProperty("passcount")
    private int passCount;
    @JsonProperty("playcount")
    private int playCount;
    /** See {@link RankStatus} for a list of possible values. */
    private int ranked;
    private String url;

}