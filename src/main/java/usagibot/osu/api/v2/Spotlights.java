package usagibot.osu.api.v2;

import lombok.Getter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Spotlights {

    /**
     * An array of spotlights.
     */
    private List<Spotlight> spotlights;

}