package usagibot.osu.api.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class NominationsSummary {

    private int current;
    private int required;

}