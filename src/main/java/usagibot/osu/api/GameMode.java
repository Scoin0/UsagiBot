package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: Available game modes
 * URL:         https://osu.ppy.sh/docs/index.html?bash#gamemode
 */
public enum GameMode {

    CATCH("fruits"),
    MAINA("mania"),
    OSU("osu"),
    TAIKO("taiko");

    @JsonValue
    private String name;

    GameMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
