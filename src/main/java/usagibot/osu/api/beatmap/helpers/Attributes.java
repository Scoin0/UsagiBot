package usagibot.osu.api.beatmap.helpers;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import usagibot.osu.api.beatmap.BeatmapDifficultyAttributes;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Attributes {

    private BeatmapDifficultyAttributes attributes;

}