package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RankedStatus {

    GRAVEYARD(-1, "graveyard"),
    WIP(-2, "wip"),
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

