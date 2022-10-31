package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Available game modes
 * URL:         https://osu.ppy.sh/docs/index.html?bash#gamemode
 */
public enum GameMode {

    OSU("osu", 0),
    TAIKO("taiko", 1),
    CATCH("fruits", 2),
    MAINA("mania", 3);

    @JsonValue
    private String name;
    @JsonProperty("mode")
    private int id;

    GameMode(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
