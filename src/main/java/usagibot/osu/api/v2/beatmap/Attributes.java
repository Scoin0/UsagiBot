package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Attributes {

    private BeatmapDifficultyAttributes attributes;

}
