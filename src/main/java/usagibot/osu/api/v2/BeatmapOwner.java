package usagibot.osu.api.v2;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Users that are no longer visible will still appear as a {@code BeatmapOwner} but have the username set to [deleted user].
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BeatmapOwner {

    /**
     * User id of the Beatmap owner.
     */
    private int id;
    /**
     * Username of the Beatmap owner.
     */
    private String username;

}