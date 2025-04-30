package usagibot.osu.api.v2.user;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A record indicating a {@link User} was silenced.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserSilence {

    /**
     * id of this object.
     */
    private int id;
    /**
     * id of the {@link User} that was silenced.
     */
    @JsonProperty("user_id")
    private int userId;

}