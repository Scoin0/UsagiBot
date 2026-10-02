package usagibot.osu.api.user;

import lombok.Getter;
import usagibot.osu.api.enums.Ruleset;
import usagibot.osu.api.user.helpers.Level;
import usagibot.osu.api.user.helpers.GradeCounts;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A summary of various gameplay statistics for a {@link User}. Specific to a {@link Ruleset}.
 * @see <a href=https://osu.ppy.sh/docs/#userstatistics>#userstatistics</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserStatistics {

    @JsonProperty("count_miss")
    private int countMiss;
    @JsonProperty("count_50")
    private int count50;
    @JsonProperty("count_100")
    private int count100;
    @JsonProperty("count_300")
    private int count300;
    @JsonProperty("country_rank")
    private int countryRank;
    @JsonProperty("grade_counts")
    private GradeCounts gradeCounts;
    private float accuracy;
    @JsonProperty("is_ranked")
    private boolean isRanked;
    private Level level;
    @JsonProperty("maximum_combo")
    private int maximumCombo;
    @JsonProperty("play_count")
    private int playCount;
    @JsonProperty("play_time")
    private int playTime;
    private float pp;
    @JsonProperty("global_rank")
    private int globalRank;
    @JsonProperty("ranked_score")
    private int rankedScore;
    @JsonProperty("replays_watched_by_others")
    private int replaysWatchedByOthers;
    @JsonProperty("total_hits")
    private int totalHits;
    @JsonProperty("total_score")
    private int totalScore;

    // Optional Attributes
    @JsonProperty("rank_change_since_30_days")
    private int rankChange;
    private User user;

}