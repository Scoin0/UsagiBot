package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represent the playcount of a beatmap.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapPlayCount {

    @JsonProperty("beatmap_id")
    private int beatmapId;
    private Beatmap beatmap;
    private Beatmapset beatmapset;
    private int count;

}