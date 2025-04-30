package usagibot.osu.api.v2.enums;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

@Getter
public enum ProfilePage {

    ME("me"),
    RECENT_ACTIVITY("recent_activity"),
    BEATMAPS("beatmaps"),
    HISTORICAL("historical"),
    KUDOSU("kudosu"),
    TOP_RANKS("top_ranks"),
    MEDALS("medals");

    @JsonValue
    private final String name;

    ProfilePage(String name) {
        this.name = name;
    }

    @JsonCreator
    public static ProfilePage fromValue(String value) {
        for (ProfilePage profilePage : values()) {
            if (profilePage.name.equalsIgnoreCase(value)) {
                return profilePage;
            }
        }
        throw new IllegalArgumentException("Unknown ProfilePage: " + value);
    }
}