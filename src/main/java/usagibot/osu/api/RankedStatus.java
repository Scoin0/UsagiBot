package usagibot.osu.api;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Follows the osu!web documentation (As of July 26th, 2022)
 * Description: The possible values are denoted either as integer or string.
 * URL:         https://osu.ppy.sh/docs/index.html?bash#beatmapsetcompact-rank-status
 */
public enum RankedStatus {

    GRAVEYARD(-2, "graveyard"),
    WIP(-1, "wip"),
    PENDING(0, "pending"),
    RANKED(1, "ranked"),
    APPROVED(2, "approved"),
    QUALIFIED(3, "qualified"),
    LOVED(4, "loved");

    private int id;
    @JsonValue
    private String name;

    RankedStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

