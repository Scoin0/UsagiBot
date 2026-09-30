package usagibot.osu.api.v2.match;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.Beatmap;
import java.time.OffsetDateTime;
import usagibot.osu.api.BeatmapSet;
import usagibot.osu.api.v2.Score;
import usagibot.osu.api.v2.enums.Ruleset;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MatchGame {

    private int id;
    /**
     * Includes {@link BeatmapSet}.
     */
    private Beatmap beatmap;
    @JsonProperty("beatmap_id")
    private int beatmapId;
    @JsonProperty("start_time")
    private OffsetDateTime startTime;
    @JsonProperty("end_time")
    private OffsetDateTime endTime;
    private Ruleset mode;
    /**
     * Mod combination used for this match game as an array of mod acronyms.
     */
    private List<String> mods;
    /**
     * List of scores set by each player for this match game.
     */
    private List<Score> scores;
    /**
     * {@code accuracy}, {@code combo}, {@code score}, {@code scorev2}.
     */
    @JsonProperty("scoring_type")
    private String scoringType;
    /**
     * {@code head-to-head}, {@code tag-coop}, {@code tag-team-vs}, {@code team-vs}.
     */
    @JsonProperty("team_type")
    private String teamType;

}