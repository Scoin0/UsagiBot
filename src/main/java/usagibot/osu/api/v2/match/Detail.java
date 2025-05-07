package usagibot.osu.api.v2.match;

import lombok.Getter;
import usagibot.osu.api.v2.enums.MatchEventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Detail {

    private MatchEventType type;
    private String text;

}