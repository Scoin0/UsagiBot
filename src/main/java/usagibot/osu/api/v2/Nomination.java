package usagibot.osu.api.v2;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Nomination {

    @JsonProperty("beatmapset_id")
    private int beatmapsetId;
    private List<Ruleset> rulesets;
    private boolean reset;
    @JsonProperty("user_id")
    private int userId;

}