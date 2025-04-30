package usagibot.osu.api.v2.beatmap;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class NominationsSummary {

    private int current;
    private int required;

}