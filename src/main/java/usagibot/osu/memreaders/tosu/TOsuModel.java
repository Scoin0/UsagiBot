package usagibot.osu.memreaders.tosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Follows the v1 websocket api response of tOsu (As of March 10th, 2024)
 * URL:         https://github.com/KotRikD/tosu/wiki/v1-websocket-api-response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TOsuModel {

    // Ignore Settings
    @JsonProperty("menu")
    public Menu menu;
    @JsonProperty("gameplay")
    public Gameplay gameplay;
    @JsonProperty("userProfile")
    public UserProfile userProfile;
    // Ignore ResultsScreen
    // Ignore Tourney

}