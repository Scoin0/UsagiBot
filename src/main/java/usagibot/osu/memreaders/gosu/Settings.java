package usagibot.osu.memreaders.gosu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings {

    @JsonProperty("folders")
    public Folders folders;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Folders {
        @JsonProperty("skin")
        public String skin;
    }

    //Ignore everything else.

}
