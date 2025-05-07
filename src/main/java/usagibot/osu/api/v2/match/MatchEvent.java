package usagibot.osu.api.v2.match;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MatchEvent {

    private int id;
    private Detail detail;
    private OffsetDateTime timestamp;
    @JsonProperty("user_id")
    private Integer userId;

    // Optional Attributes
    /**
     * The game associated with the {@link MatchEvent}
     */
    private MatchGame game;

}