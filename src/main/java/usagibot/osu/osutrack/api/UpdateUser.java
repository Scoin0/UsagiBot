package usagibot.osu.osutrack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import usagibot.osu.api.GameMode;
import usagibot.utils.JsonObjectListDeserializer;
import java.util.Date;
import java.util.List;

/**
 * Follows the osu!tracker api documentation (As of Sept. 26th, 2022)
 * Description: Difference in stats since the last update if successful
 * URL:         https://github.com/Ameobea/osutrack-api
 * POST | {user}, {mode}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UpdateUser {

    @JsonProperty("username")
    private String username;
    @JsonProperty("mode")
    private GameMode mode;
    @JsonProperty("playcount")
    private int playeCount;
    @JsonProperty("pp_rank")
    private int ppRank;
    @JsonProperty("pp_raw")
    private float ppRaw;
    @JsonProperty("accuracy")
    private float accuracy;
    @JsonProperty("total_score")
    private long totalScore;
    @JsonProperty("ranked_score")
    private long rankedScore;
    @JsonProperty("count300")
    private int count300;
    @JsonProperty("count50")
    private int count50;
    @JsonProperty("count100")
    private int count100;
    @JsonProperty("level")
    private float level;
    @JsonProperty("count_rank_a")
    private int countRankA;
    @JsonProperty("count_rank_s")
    private int countRankS;
    @JsonProperty("count_rank_ss")
    private int countRankSS;
    @JsonProperty("levelup")
    private boolean levelUp;
    @JsonProperty("first")
    private boolean first;
    @JsonProperty("exists")
    private boolean exists;
    @JsonProperty("newhs")
    //@JsonDeserialize(using = JsonObjectListDeserializer.class)
    private List<newHighscores> newHighscores;

    public static class newHighscores {

        @JsonProperty("beatmap_id")
        private int beatmapId;
        @JsonProperty("score_id")
        private long scoreId;
        @JsonProperty("score")
        private long score;
        @JsonProperty("maxcombo")
        private int maxCombo;
        @JsonProperty("count50")
        private int count50;
        @JsonProperty("count100")
        private int count100;
        @JsonProperty("count300")
        private int count300;
        @JsonProperty("countmiss")
        private int miss;
        @JsonProperty("countkatu")
        private int countKatu;
        @JsonProperty("countgeki")
        private int countGeki;
        @JsonProperty("perfect")
        private int perfect;
        @JsonProperty("enabled_mods")
        private int enabledMods;
        @JsonProperty("user_id")
        private long userId;
        @JsonProperty("date")
        private Date date;
        @JsonProperty("rank")
        private String rank;
        @JsonProperty("pp")
        private float pp;
        @JsonProperty("replay_available")
        private int replayAvailable;
        @JsonProperty("ranking")
        private int ranking;
    }
}
