package usagibot.osu.osutrack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import usagibot.osu.api.Mods;
import java.util.Date;

/**
 * Follows the osu!tracker api documentation (As of Sept. 26th, 2022)
 * Description: Difference in stats since the last update if successful
 * URL:         https://github.com/Ameobea/osutrack-api
 * GET | {user}, {mode}, {from}, {to}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class HiScores {

    @JsonProperty("beatmap_id")
    private int beatmapId;
    @JsonProperty("score")
    private long score;
    @JsonProperty("pp")
    private float pp;
    @JsonProperty("mods")
    private Mods mods;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("score_time")
    private Date scoreTime;
    @JsonProperty("update_time")
    private Date updateTime;

}
