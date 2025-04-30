package usagibot.osu.api.v2.user;

import lombok.Getter;
import usagibot.osu.api.v2.enums.Ruleset;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Describes a {@link Group} membership of a {@link User}. It contains all the attributes of the
 * {@link Group}, in addition to what is listed here.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserGroup {

    /**
     * {@link Ruleset Rulesets} associated with this membership (null if {@code has_playmodes} is unset).
     */
    private String playmodes;

}