package usagibot.osu.api.v2.user;

import lombok.Getter;
import usagibot.osu.api.v2.enums.Ruleset;
import usagibot.osu.api.v2.beatmap.GradeCounts;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;

/**
 * A summary of various gameplay statistics for a {@link User}. Specific to a {@link Ruleset}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserStatistics {

    @JsonProperty("count_100")
    private int count100;
    @JsonProperty("count_300")
    private int count300;
    @JsonProperty("count_50")
    private int count50;
    @JsonProperty("count_miss")
    private int countMiss;
    /**
     * Current country rank according to pp.
     */
    @JsonProperty("country_rank")
    private Integer countryRank;
    @JsonProperty("grade_counts")
    private GradeCounts gradeCounts;
    /**
     * Hit accuracy percentage
     */
    @JsonProperty("hit_accuracy")
    private float hitAccuracy;
    /**
     * Is actively ranked
     */
    @JsonProperty("is_ranked")
    private boolean isRanked;
    private Level level;
    /**
     * Highest maximum combo.
     */
    @JsonProperty("maximum_combo")
    private int maximumCombo;
    /**
     * Number of maps played.
     */
    @JsonProperty("play_count")
    private int playCount;
    /**
     * Cumulative time played.
     */
    @JsonProperty("play_time")
    private int playTime;
    /**
     * Performance points
     */
    private float pp;
    /**
     * Experimental (lazer) performance points. Deprecated; it's now always 0.
     */
    @JsonProperty("pp_exp")
    private float ppExp;
    /**
     * Current rank according to pp.
     */
    @JsonProperty("global_rank")
    private Integer globalRank;
    /**
     * Current rank according to experimental (lazer) pp. Deprecated; it's now always null.
     */
    @JsonProperty("global_rank_exp")
    private Integer globalRankExp;
    /**
     * Current ranked score.
     */
    @JsonProperty("ranked_score")
    private Long rankedScore;
    /**
     * Number of replays watched by other users.
     */
    @JsonProperty("replays_watched_by_others")
    private int replaysWatchedByOthers;
    /**
     * Total number of hits.
     */
    @JsonProperty("total_hits")
    private int totalHits;
    /**
     * Total score.
     */
    @JsonProperty("total_score")
    private Long totalScore;

    // Optional Attributes
    /**
     * Difference between current rank and rank 30 days ago, according to pp.
     */
    @JsonProperty("rank_change_since_30_days")
    private Integer rankChangedIn30Days;
    private User user;

}