package usagibot.osu.api.v2.user;

import lombok.Getter;
import java.util.List;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a user.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = User.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserExtended.class)
})
public class User {

    /**
     * URL of a user's avatar
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /**
     * Two-Letter code representing user's country
     */
    @JsonProperty("country_code")
    private String countryCode;
    /**
     * Identifier of the default Group the user belongs to
     */
    @JsonProperty("default_group")
    private String defaultGroup;
    /**
     * Unique identifier for user
     */
    private int id;
    /**
     * Has this account been active in the last x months?
     */
    @JsonProperty("is_active")
    private boolean isActive;
    /**
     * Is this a bot account?
     */
    @JsonProperty("is_bot")
    private boolean isBot;
    @JsonProperty("is_deleted")
    private boolean isDeleted;
    /**
     * Is the user currently online? (Either on Lazer or new the website)
     */
    @JsonProperty("is_online")
    private boolean isOnline;
    /**
     * Does this user have supporter?
     */
    @JsonProperty("is_supporter")
    private boolean isSupporter;
    /**
     * Last access time. {@code null} if the user hides online presence
     */
    @JsonProperty("last_visit")
    private OffsetDateTime lastVisit;
    /**
     * Whether the user allows PMs from other than friends
     */
    @JsonProperty("pm_friends_only")
    private boolean pmFriendsOnly;
    /**
     * Color of the username/profile highlight, hex code (e.g. {@code #333333}
     */
    @JsonProperty("profile_colour")
    private String profileColor;
    /**
     * User's display name
     */
    private String username;

    // Optional Attributes | Following are attributes which may be additionally included in the response. Relevant endpoints should list them if applicable.
    @JsonProperty("user_account_history")
    private List<UserAccountHistory> accountHistory;
    private List<ProfileBanner> tournamentBanner;
    private List<UserBadge> badges;
    private Cover cover;
    @JsonProperty("beatmap_playcount_count")
    private int beatmapPlaycountsCount;
    @JsonProperty("favourite_beatmapset_count")
    private int favouriteBeatmapsetCount;
    @JsonProperty("follow_user_mapping")
    private List<Integer> followUserMapping;
    @JsonProperty("follower_count")
    private int followerCount;
    @JsonProperty("graveyard_beatmapset_count")
    private int graveyardBeatmapsetCount;
    private List<UserGroup> groups;
    @JsonProperty("guest_beatmapset_count")
    private int guestBeatmapsetCount;
    @JsonProperty("is_restricted")
    private Boolean isRestricted;
    private Kudosu kudosu;
    @JsonProperty("loved_beatmapset_count")
    private int lovedBeatmapsetCount;
    @JsonProperty("mapper_following_count")
    private int mapperFollowingCount;
    @JsonProperty("nominated_beatmapset_count")
    private int nominatedBeatmapsetCount;
    @JsonProperty("rank_highest")
    private List<RankHighest> rankHighest;
    @JsonProperty("scores_best_count")
    private int scoresBestCount;
    @JsonProperty("scores_first_count")
    private int scoresFirstCount;
    @JsonProperty("scores_recent_count")
    private int scoresRecentCount;
    @JsonProperty("session_verified")
    private boolean sessionVerified;
    private UserStatistics statistics;

}