package usagibot.osu.objects;

import com.fasterxml.jackson.annotation.JsonValue;

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
