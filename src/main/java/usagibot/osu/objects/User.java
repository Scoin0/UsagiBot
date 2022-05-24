package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Slf4j
public class User extends UserCompact {

    // Represents a User.
    @JsonProperty("cover.url")
    private String cover_url;
    @JsonProperty("discord")
    private String discord;
    @JsonProperty("has_supported")
    private boolean has_supported;
    @JsonProperty("interests")
    private String interests;
    @JsonProperty("join_date")
    private Timestamp join_date;
    @JsonProperty("kudosu.avaliable")
    private int kudosu_avaliable;
    @JsonProperty("kudosu.total")
    private int kudosu_total;
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
    //@JsonProperty("playstyle")
    //private String playstyle;
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

    // UserStatistics
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

    @Getter
    public static class Level {
        public int current;
        public int progress;
    }

    @Getter
    public static class GradeCounts {
        public int a;
        public int s;
        public int sh;
        public int ss;
        public int ssh;
    }
}