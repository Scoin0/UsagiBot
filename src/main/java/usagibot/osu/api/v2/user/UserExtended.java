package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.util.List;
import java.time.OffsetDateTime;
import usagibot.osu.api.v2.enums.Ruleset;
import usagibot.osu.api.v2.enums.ProfilePage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a user. Extends {@link User} object with additional attributes.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserExtended extends User {

    /**
     * Url of profile cover.
     * @deprecated Use {@code cover.url} instead.
     */
    @JsonProperty("cover_url")
    private String coverUrl;
    private String discord;
    /**
     * Whether the user has a current or past osu!supporter tag.
     */
    @JsonProperty("has_supported")
    private boolean hasSupported;
    private String interests;
    @JsonProperty("join_date")
    private OffsetDateTime joinDate;
    private String location;
    /**
     * Maximum number of users allowed to be blocked.
     */
    @JsonProperty("max_blocks")
    private int maxBlocks;
    /**
     * Maximum numbers of friends allowed to be added.
     */
    @JsonProperty("max_friends")
    private int maxFriends;
    private String occupation;
    private Ruleset playmode;
    /**
     * Device choices of the user.
     */
    private List<String> playstyle;
    @JsonProperty("post_count")
    private int postCount;
    /**
     * Custom colour hue in HSL degrees (0-359).
     */
    private Integer profileHue;
    /**
     * Ordered array of sections in user profile page.
     */
    @JsonProperty("profile_order")
    private List<ProfilePage> profileOrder;
    /**
     * User-specific title.
     */
    private String title;
    @JsonProperty("title_url")
    private String titleUrl;
    private String twitter;
    private String website;

}