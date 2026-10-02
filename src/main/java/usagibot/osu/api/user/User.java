package usagibot.osu.api.user;

import lombok.Getter;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a user.
 * @see <a href=https://osu.ppy.sh/docs/#user>#user</a>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class User {

    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("country_code")
    private String countryCode;
    private int id;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("is_bot")
    private boolean isBot;
    @JsonProperty("is_deleted")
    private boolean isDeleted;
    @JsonProperty("is_online")
    private boolean isOnline;
    @JsonProperty("is_supporter")
    private boolean isSupporter;
    @JsonProperty("last_visit")
    private OffsetDateTime lastVisit;
    private String username;

    // Optional Attributes
    @JsonProperty("beatmap_playcounts_count")
    private int beatmapPlaycountsCount;
    @JsonProperty("favourite_beatmapset_count")
    private int favouriteBeatmapsetCount;
    @JsonProperty("follower_count")
    private int followerCount;
    @JsonProperty("is_restricted")
    private boolean isRestricted;
    @JsonProperty("scores_best_count")
    private int scoresBestCount;
    @JsonProperty("scores_first_count")
    private int scoresFirstCount;
    @JsonProperty("scores_recent_count")
    private int scoresRecentCount;
    @JsonProperty("statistics")
    private UserStatistics userStatistics;

}