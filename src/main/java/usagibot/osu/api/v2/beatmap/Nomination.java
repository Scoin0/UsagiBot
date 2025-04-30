package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.v2.enums.Ruleset;
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