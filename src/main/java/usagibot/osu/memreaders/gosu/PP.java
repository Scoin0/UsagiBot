package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PP {

    @JsonProperty("current")
    public float current;
    @JsonProperty("fc")
    public float fc;
    @JsonProperty("maxThisPlay")
    public float maxThisPlay;

}
