package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.sql.Timestamp;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Mainly used for embedded in certain responses to save additional api lookups.
 * URL:         https://osu.ppy.sh/docs/index.html?bash#usercompact
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserCompact {

    @JsonProperty("avatar_url")
    private String avatar_url;
    @JsonProperty("country_code")
    private String country_code;
    @JsonProperty("default_group")
    private String default_group;
    @JsonProperty("id")
    private int id;
    @JsonProperty("is_active")
    private boolean is_active;
    @JsonProperty("is_bot")
    private boolean is_bot;
    @JsonProperty("is_deleted")
    private boolean is_deleted;
    @JsonProperty("is_online")
    private boolean is_online;
    @JsonProperty("is_supporter")
    private boolean is_supporter;
    @JsonProperty("last_visit")
    private Timestamp last_visit;
    @JsonProperty("pm_friends_only")
    private boolean pm_friends_only;
    @JsonProperty("profile_colour")
    private String profile_colour;
    @JsonProperty("username")
    private String username;

    /**
     * Optional Attributes:
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Following are attributes which may be additionally included in the response. Relevant endpoints should list them if applicable.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#usercompact
     */
    @JsonProperty("account_history")
    private AccountUserHistory[] account_history;
    @JsonProperty("active_tournament_banner")
    private ProfileBanner active_tournament_banner;
    @JsonProperty("badges")
    private UserBadge[] badges;
    @JsonProperty("beatmap_playcount_count")
    private int beatmap_playcount_count;
    @JsonProperty("favourite_beatmapset_count")
    private int favourite_beatmapset_count;
    @JsonProperty("follower_count")
    private int follower_count;
    @JsonProperty("graveyard_beatmapset_count")
    private int graveyard_beatmapset_count;
    @JsonProperty("is_restricted")
    private boolean is_restricted;
    @JsonProperty("loved_beatmapset_count")
    private int loved_beatmapset_count;
    @JsonProperty("scores_best_count")
    private int scores_best_count;
    @JsonProperty("scores_first_count")
    private int scores_first_count;
    @JsonProperty("scores_recent_count")
    private int scores_recent_count;

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Grabs information from the profile banner section.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#usercompact
     */
    public static class ProfileBanner {

        private int id;
        private int tournament_id;
        private String image;

        public int getId() {
            return id;
        }

        public int getTournament_Id() {
            return tournament_id;
        }

        public String getImage() {
            return image;
        }
    }

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Grabs information from the account user history section.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#usercompact
     */
    public static class AccountUserHistory {

        private String[] description;
        private int id;
        private int length;
        private Timestamp timestamp;
        private String type;

        public String[] getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public int getLength() {
            return length;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * Follows the osu!web documentation (As of July 26th, 2022)
     * Description: Grabs information from the user badge section.
     * URL:         https://osu.ppy.sh/docs/index.html?bash#usercompact
     */
    public static class UserBadge {

        private Timestamp timestamp;
        private String description;
        private String image_url;
        private String url;

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public String getDescription() {
            return description;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getUrl() {
            return url;
        }
    }
}
