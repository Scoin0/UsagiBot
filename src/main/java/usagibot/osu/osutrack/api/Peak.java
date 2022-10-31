package usagibot.osu.osutrack.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Follows the osu!tracker api documentation (As of Sept. 26th, 2022)
 * Description: Difference in stats since the last update if successful
 * URL:         https://github.com/Ameobea/osutrack-api
 * GET | {user}, {mode}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Peak {

    @JsonProperty("best_global_rank")
    private int bestGlobalRank;
    @JsonProperty("best_accuracy")
    private long bestAccuracy;

}
