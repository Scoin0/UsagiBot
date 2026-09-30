package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import usagibot.osu.api.v2.Score;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapUserScore {

    /**
     * The position of the score within the requested beatmap ranking.
     */
    private int position;
    /**
     * The details of the score.
     */
    private Score score;

}