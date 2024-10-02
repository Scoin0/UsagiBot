package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Follows the JSON schema (As of March 10th, 2024)
 * URL:         https://github.com/l3lackShark/gosumemory/wiki/JSON-values
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GOsuModel {

    @JsonProperty("settings")
    public Settings settings;
    @JsonProperty("menu")
    public Menu menu;
    @JsonProperty("gameplay")
    public Gameplay gameplay;
    @JsonProperty("pp")
    public PP pp;
    // Ignore LeaderBoard
}
