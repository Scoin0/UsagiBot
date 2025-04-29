package usagibot.osu.api.v2;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Level {

    /**
     * Current level.
     */
    private int current;
    /**
     * Progress to next level.
     */
    private float progress;

}