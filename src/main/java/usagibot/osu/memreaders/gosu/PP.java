package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PP {

    @JsonProperty("current")
    public float current;
    @JsonProperty("fc")
    public float fc;
    @JsonProperty("maxThisPlay")
    public float maxThisPlay;

}
