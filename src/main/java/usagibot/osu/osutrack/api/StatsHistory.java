package usagibot.osu.osutrack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.Date;

/**
 * Follows the osu!tracker api documentation (As of Sept. 26th, 2022)
 * Description: Difference in stats since the last update if successful
 * URL:         https://github.com/Ameobea/osutrack-api
 * GET | {user}, {mode}, {from}, {to}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class StatsHistory {

    @JsonProperty("count300")
    private int count300;
    @JsonProperty("count100")
    private int count100;
    @JsonProperty("count50")
    private int count50;
    @JsonProperty("playcount")
    private int playCount;
    @JsonProperty("ranked_score")
    private long rankedScore;
    @JsonProperty("total_score")
    private long totalScore;
    @JsonProperty("pp_rank")
    private int ppRank;
    @JsonProperty("level")
    private float level;
    @JsonProperty("pp_raw")
    private float ppRaw;
    @JsonProperty("accuracy")
    private float accuracy;
    @JsonProperty("count_rank_ss")
    private int countRankSS;
    @JsonProperty("count_rank_s")
    private int countRankS;
    @JsonProperty("count_rank_a")
    private int countRankA;
    @JsonProperty("timestamp")
    private Date timeStamp;

}
