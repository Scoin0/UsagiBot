package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.util.List;
import usagibot.osu.api.v2.enums.Ruleset;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This object is not returned by any endpoints yet. It is here only as a reference for
 * {@link UserGroup}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Group {

    private String colour;
    /**
     * Whether this group displays a listing at {@code /groups/{id}}.
     */
    @JsonProperty("has_listing")
    private boolean hasListing;
    /**
     * Whether this group associates {@link Ruleset Rulesets} with users' memberships.
     */
    @JsonProperty("has_playmodes")
    private boolean hasPlaymodes;
    private int id;
    /**
     * Unique string to identify the group.
     */
    private String identifier;
    /**
     * Whether members of this group are considered probationary.
     */
    @JsonProperty("is_probationary")
    private boolean isProbationary;
    private String name;
    /**
     * Short name of the group for display.
     */
    @JsonProperty("short_name")
    private String shortName;

    // Optional Attributes | The following are attributes which may be additionally included in responses. Relevant endpoints should list them if applicable.
    private List<Description> description;

}