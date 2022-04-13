package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UserCompact {

    // Mainly used for embedding in certain responses to save additional api lookups
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

    // Optional attributes
    @JsonProperty("beatmap_playcount_count")
    private int beatmap_playcount_count;
    @JsonProperty("favourite_beatmapset_count")
    private int favourite_beatmapset_count;
    @JsonProperty("follower_count")
    private int follower_count;
    @JsonProperty("is_restricted")
    private boolean is_restricted;

}
