package usagibot.osu.api.user.helpers;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Level {

    private int current;
    private float progress;

}