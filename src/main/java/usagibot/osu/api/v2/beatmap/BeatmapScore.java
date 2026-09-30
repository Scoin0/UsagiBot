package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.v2.Score;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapScore {

    /**
     * The list of top scores for the beatmap in descending order.
     */
    private List<Score> scores;
    /**
     * The score of the current user. This is not returned if the current user does not have a score.
     * Note: Will be moved to {@code user_score} in the future.
     */
    private BeatmapUserScore userScore;

}