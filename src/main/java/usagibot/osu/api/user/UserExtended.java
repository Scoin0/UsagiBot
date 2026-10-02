package usagibot.osu.api.user;

import lombok.Getter;
import java.time.OffsetDateTime;
import usagibot.osu.api.enums.Ruleset;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a user. Extends {@link User} object with additional attributes.
 * @see <a href=https://osu.ppy.sh/docs/#userextended><#userextended/a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserExtended extends User {

    @JsonProperty("has_supported")
    private boolean hasSupported;
    @JsonProperty("join_date")
    private OffsetDateTime joinDate;
    @JsonProperty("play_mode")
    private Ruleset playMode;
    @JsonProperty("post_count")
    private int postCount;

}