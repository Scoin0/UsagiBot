package usagibot.osu.api.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent the playcount of a beatmap.
 * @link <a href=https://osu.ppy.sh/docs/#beatmapplaycount>#beatmapplaycount</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapPlaycount {

    @JsonProperty("beatmap_id")
    private int beatmapId;
    private Beatmap beatmap;
    private Beatmapset beatmapset;
    private int count;

}