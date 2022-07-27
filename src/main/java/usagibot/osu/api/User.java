package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Represents a user. This extends UserCompact with additional attributes.
 * URL:         https://osu.ppy.sh/docs/index.html?bash#user
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class User extends UserCompact {

    @JsonProperty("discord")
    private String discord;
    @JsonProperty("has_supported")
    private boolean has_supported;
    @JsonProperty("interests")
    private String interests;
    @JsonProperty("join_date")
    private Timestamp join_date;
    @JsonProperty("location")
    private String location;
    @JsonProperty("max_blocks")
    private int max_blocks;
    @JsonProperty("max_friends")
    private int max_friends;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("playmode")
    private GameMode playmode;
    @JsonProperty("post_count")
    private int post_count;
    @JsonProperty("title")
    private String title;
    @JsonProperty("title_url")
    private String title_url;
    @JsonProperty("twitter")
    private String twitter;
    @JsonProperty("website")
    private String website;
    @JsonProperty("statistics")
    private UserStatistics statistics;

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Represents the user statistics found within osu!web.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#user
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class UserStatistics {
        public int hit_accuracy;
        public boolean is_ranked;
        public Level level;
        public int maximum_combo;
        public int play_count;
        public int play_time;
        public int pp;
        public int global_rank;
        public BigInteger ranked_score;
        public int replays_watched_by_others;
        public BigInteger total_hits;
        public BigInteger total_score;
        public GradeCounts grade_counts;
    }

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Grabs the current level of a user within the user statistics.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#user
     */
    @Getter
    public static class Level {
        public int current;
        public int progress;
    }

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Grabs the grade counts of a user within the user statistics.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#user
     */
    @Getter
    public static class GradeCounts {
        public int a;
        public int s;
        public int sh;
        public int ss;
        public int ssh;
    }
}